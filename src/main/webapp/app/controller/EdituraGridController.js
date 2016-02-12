Ext.define('BM.controller.EdituraGridController', {
    extend: 'Ext.app.Controller',
    requires: [
        'Ext.window.MessageBox'
    ],
    stores: [
        'EdituraStore'
    ],

    views: [
        'editura.EdituraWindow',
        'ErrorWindow'
    ],

    init: function () {
        this.control({
            'edituragrid': {
                selectionchange: this.changeselectionEditura,
                celldblclick: this.celldblclickEditura,
                render: this.refreshEdituraGrid,
                itemcontextmenu: this.itemContextMenu,
                containercontextmenu: this.showMenu
            },
            'edituragrid button[action=add-editura]': {
                click: this.addEditura
            },
            'edituragrid button[action=mod-editura]': {
                click: this.modEditura
            },
            'edituragrid button[action=del-editura]': {
                click: this.delEditura
            },
            'edituragrid button[action=refresh-editura]': {
                click: this.refreshEdituraGrid
            },
            'editurawindow button[itemId=saveBtn]': {
                click: this.saveEditura
            },
            'editurawindow button[itemId=cancelBtn]': {
                click: this.closeEdituraWindow
            }
        });
    },

    changeselectionEditura: function (selModel, selected, eOpts) {
        enableEdituraGridButtons(selected.length > 0);
    },

    celldblclickEditura: function (grid, td, cellIndex, record, tr, rowIndex, e) {
        var modButton = Ext.ComponentQuery.query('edituragrid button[action=mod-editura]')[0];
        this.modEditura(modButton);
    },

    addEditura: function (button, clickEvent, options) {
        var window = Ext.widget('editurawindow');
        window.show();
    },

    modEditura: function (button, clickEvent, options) {
        var grid = Ext.ComponentQuery.query('edituragrid')[0];
        var selectionModel = grid.getSelectionModel();
        if (!selectionModel.hasSelection) {
            Ext.Msg.show({
                title: 'Editura neselectata',
                msg: 'Selectati o editura',
                width: 300,
                buttons: Ext.Msg.OK,
                icon: Ext.window.MessageBox.WARNING
            });
            return;
        }
        var selectedEditura = selectionModel.getSelection()[0];
        var idEditura = selectedEditura.get('idEditura');
        BM.model.EdituraModel.load(idEditura, {
            scope: this,
            failure: function (record, operation) {
                grid.getStore().remove(selection);
                var error = {
                    windowTitle: 'Error loading editura with id [' + operation.id + ']',
                    httpStatus: operation.error.status + ' (' + operation.error.statusText + ')',
                    errorMessage: operation.error.status + ' (' + operation.error.statusText + ')',
                    method: operation.request.method,
                    url: operation.request.url
                };
                createGenericErrorWindow(error);
            },
            success: function (record, operation) {
                var window = Ext.widget('editurawindow');
                var edituraForm = window.down('form[itemId=edituraform]');
                edituraForm.loadRecord(record);
                window.show();
            }
        });
    },

    delEditura: function (button) {
        Ext.MessageBox.confirm('Confirmare', 'Sunteti sigur?', this.deleteEditura);
    },

    deleteEditura: function (btn) {
        if (btn == 'yes') {
            var edituragrid = Ext.ComponentQuery.query('edituragrid')[0];
            var selectionModel = edituragrid.getSelectionModel();
            if (!selectionModel.hasSelection) {
                Ext.Msg.show({
                    title: 'Editura neselectata',
                    msg: 'Selectati o editura',
                    width: 300,
                    buttons: Ext.Msg.OK,
                    icon: Ext.window.MessageBox.WARNING
                });
                return;
            }
            var selectedEditura = selectionModel.getSelection()[0];
            Ext.Ajax.request({
                url: '/editura/' + selectedEditura.get('idEditura'),
                method: 'DELETE',
                scope: this,
                success: function (result, request) {
                    enableEdituraGridButtons(false);
                    edituragrid.getStore().remove(selectedEditura);
                    var comboStore = Ext.StoreMgr.lookup('EdituraComboStore');
                    comboStore.remove(selectedEditura);
                },
                failure: function (result, request) {
                    createErrorWindow(result);
                }
            });
        }
    },

    closeEdituraWindow: function (button, clickEvent, options) {
        var window = button.up('editurawindow');
        window.close();
    },

    saveEditura: function (button, clickEvent, options) {
        var form = button.up('editurawindow').down('form[itemId=edituraform]');
        var edituraId = form.down('hidden[name=idEditura]').getValue();
        var isAdd = Ext.isEmpty(edituraId);
        var title = form.down('textfield[name=numeEditura]').getValue();
        if (form.isValid()) {
            Ext.Ajax.request({
                url: '/editura',
                method: isAdd ? 'POST' : 'PUT',
                headers: {'Content-Type': 'application/json'},
                params: Ext.JSON.encode(form.getValues()),
                success: function (response) {
                    button.up('editurawindow').close();
                    enableEdituraGridButtons(false);
                    var grid = Ext.ComponentQuery.query('edituragrid')[0];
                    var idEditura = JSON.parse(response.responseText)['idEditura'];
                    BM.model.EdituraModel.load(idEditura, {
                        scope: this,
                        failure: function (record, operation) {
                            var error = {
                                windowTitle: 'Error loading editura with id [' + operation.id + ']',
                                httpStatus: operation.error.status + ' (' + operation.error.statusText + ')',
                                errorMessage: operation.error.status + ' (' + operation.error.statusText + ')',
                                method: operation.request.method,
                                url: operation.request.url
                            };
                            createGenericErrorWindow(error);
                        },
                        success: function (record, operation) {
                            //update/add record on the grid store
                            var storeRecord = grid.getStore().getById(idEditura);
                            if (storeRecord) {
                                storeRecord.set(record.getData());
                                storeRecord.commit();
                            } else {
                                grid.getStore().add(record);
                            }


                            //update/add record on the comboAutor store
                            var comboStore = Ext.StoreMgr.lookup('EdituraComboStore');
                            var comboStoreRecord = comboStore.getById(idEditura);
                            if (comboStoreRecord) {
                                comboStoreRecord.set(record.getData());
                                comboStoreRecord.commit();
                            } else {
                                comboStore.add(record);
                            }
                            enableEdituraGridButtons(grid.getSelectionModel().getSelection().length > 0);
                        }
                    });

                },
                failure: function (result, request) {
                    createErrorWindow(result);
                }
            });
        }
    },

    refreshEdituraGrid: function (button, clickEvent, options) {
        this.getEdituraStoreStore().loadPage(1, {
            params: {
                limit: edituriPerPage
            },
            callback: function(records, operation, success) {
                if (!success) {
                    this.getEdituraStoreStore().removeAll();
                }
            }
        });
    },

    showMenu: function (panel, event, options) {
        this.configMenu(event);
    },

    itemContextMenu: function (xx, record, item, index, e, eOpts) {
        this.configMenu(e)
    },

    configMenu: function(event) {
        event.stopEvent();
        edituraMenu.showAt(event.getXY());
        edituraMenu.controller = this;
        var grid = Ext.ComponentQuery.query('edituragrid')[0];
        grid.edituraMenu = edituraMenu;
        var hasSelection = grid.getSelectionModel().hasSelection();
        edituraMenu.items.get('modEditura').setDisabled(!hasSelection);
        edituraMenu.items.get('delEditura').setDisabled(!hasSelection);
    }
});

var edituraMenu = Ext.create('Ext.menu.Menu', {
    items: [
        Ext.create('Ext.Action', {
            iconCls: 'icon-add',
            text: 'Adauga editura',
            id: 'addEditura',
            handler: function (widget, event) {
                edituraMenu.controller.addEditura();
            }
        }),
        Ext.create('Ext.Action', {
            iconCls: 'icon-mod',
            text: 'Modifica editura',
            id: 'modEditura',
            handler: function (widget, event) {
                edituraMenu.controller.modEditura();
            }
        }),
        Ext.create('Ext.Action', {
            iconCls: 'icon-delete',
            text: 'Sterge editura',
            id: 'delEditura',
            handler: function (widget, event) {
                edituraMenu.controller.delEditura();
            }
        })
    ]
});
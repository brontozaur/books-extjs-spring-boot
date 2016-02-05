Ext.define('BM.controller.CategorieGridController', {
    extend: 'Ext.app.Controller',
    requires: [
        'Ext.window.MessageBox'
    ],
    stores: [
        'CategorieStore'
    ],

    views: [
        'categorie.CategorieWindow',
        'ErrorWindow'
    ],

    init: function () {
        this.control({
            'categoriegrid': {
                selectionchange: this.changeselectionCategorie,
                celldblclick: this.celldblclickCategorie,
                render: this.refreshCategorieGrid,
                itemcontextmenu: this.itemContextMenu,
                containercontextmenu: this.showMenu
            },
            'categoriegrid button[action=add-categorie]': {
                click: this.addCategorie
            },
            'categoriegrid button[action=mod-categorie]': {
                click: this.modCategorie
            },
            'categoriegrid button[action=del-categorie]': {
                click: this.delCategorie
            },
            'categoriegrid button[action=refresh-categorie]': {
                click: this.refreshCategorieGrid
            },
            'categoriewindow button[itemId=saveBtn]': {
                click: this.saveCategorie
            },
            'categoriewindow button[itemId=cancelBtn]': {
                click: this.closeCategorieWindow
            }
        });
    },

    changeselectionCategorie: function (selModel, selected, eOpts) {
        enableCategorieGridButtons(selected.length > 0);
    },

    celldblclickCategorie: function (grid, td, cellIndex, record, tr, rowIndex, e) {
        var modButton = Ext.ComponentQuery.query('categoriegrid button[action=mod-categorie]')[0];
        this.modCategorie(modButton);
    },

    addCategorie: function (button, clickEvent, options) {
        var window = Ext.widget('categoriewindow');
        window.show();
    },

    modCategorie: function (button, clickEvent, options) {
        var grid = Ext.ComponentQuery.query('categoriegrid')[0];
        var selectionModel = grid.getSelectionModel();
        if (!selectionModel.hasSelection) {
            Ext.Msg.show({
                title: 'Categorie neselectata',
                msg: 'Selectati o categorie',
                width: 300,
                buttons: Ext.Msg.OK,
                icon: Ext.window.MessageBox.WARNING
            });
            return;
        }
        var selectedCategorie = selectionModel.getSelection()[0];
        var idCategorie = selectedCategorie.get('idCategorie');
        BM.model.CategorieModel.load(idCategorie, {
            scope: this,
            failure: function (record, operation) {
                grid.getStore().remove(selection);
                var error = {
                    windowTitle: 'Error loading categoria with id [' + operation.id + ']',
                    httpStatus: operation.error.status + ' (' + operation.error.statusText + ')',
                    errorMessage: operation.error.status + ' (' + operation.error.statusText + ')',
                    method: operation.request.method,
                    url: operation.request.url
                };
                createGenericErrorWindow(error);
            },
            success: function (record, operation) {
                var window = Ext.widget('categoriewindow');
                var categorieForm = window.down('form[itemId=categorieform]');
                categorieForm.loadRecord(selectedCategorie);
                window.show();
            }
        });
    },

    delCategorie: function (button) {
        Ext.MessageBox.confirm('Confirmare', 'Sunteti sigur?', this.deleteCategorie);
    },

    deleteCategorie: function (btn) {
        if (btn == 'yes') {
            var categoriegrid = Ext.ComponentQuery.query('categoriegrid')[0];
            var selectionModel = categoriegrid.getSelectionModel();
            if (!selectionModel.hasSelection) {
                Ext.Msg.show({
                    title: 'Categorie neselectata',
                    msg: 'Selectati o categorie',
                    width: 300,
                    buttons: Ext.Msg.OK,
                    icon: Ext.window.MessageBox.WARNING
                });
                return;
            }
            var selectedCategorie = selectionModel.getSelection()[0];
            Ext.Ajax.request({
                url: '/categorie/' + selectedCategorie.get('idCategorie'),
                method: 'DELETE',
                scope: this,
                success: function (result, request) {
                    enableCategorieGridButtons(false);
                    categoriegrid.getStore().remove(selectedCategorie);
                    var comboStore = Ext.StoreMgr.lookup('CategorieComboStore');
                    comboStore.remove(selectedCategorie);
                },
                failure: function (result, request) {
                    createErrorWindow(result);
                }
            });
        }
    },

    closeCategorieWindow: function (button, clickEvent, options) {
        var window = button.up('categoriewindow');
        window.close();
    },

    saveCategorie: function (button, clickEvent, options) {
        var form = button.up('categoriewindow').down('form[itemId=categorieform]');
        var idCategorie = form.down('hidden[name=idCategorie]').getValue();
        var isAdd = Ext.isEmpty(idCategorie);
        if (form.isValid()) {
            Ext.Ajax.request({
                url: 'categorie',
                method: isAdd ? 'POST' : 'PUT',
                headers: {'Content-Type': 'application/json'},
                params: Ext.JSON.encode(form.getValues()),
                success: function (response) {
                    button.up('categoriewindow').close();
                    enableCategorieGridButtons(false);
                    var grid = Ext.ComponentQuery.query('categoriegrid')[0];
                    var idCategorie = JSON.parse(response.responseText)['idCategorie'];
                    BM.model.CategorieModel.load(idCategorie, {
                        scope: this,
                        failure: function (record, operation) {
                            var error = {
                                windowTitle: 'Error loading categoria with id [' + operation.id + ']',
                                httpStatus: operation.error.status + ' (' + operation.error.statusText + ')',
                                errorMessage: operation.error.status + ' (' + operation.error.statusText + ')',
                                method: operation.request.method,
                                url: operation.request.url
                            };
                            createGenericErrorWindow(error);
                        },
                        success: function (record, operation) {
                            //update/add record on the grid store
                            var storeRecord = grid.getStore().getById(idCategorie);
                            if (storeRecord) {
                                storeRecord.set(record.getData());
                                storeRecord.commit();
                            } else {
                                grid.getStore().add(record);
                            }

                            //update/add record on the comboCategorie store
                            var comboStore = Ext.StoreMgr.lookup('CategorieComboStore');
                            var comboStoreRecord = comboStore.getById(idCategorie);
                            if (comboStoreRecord) {
                                comboStoreRecord.set(record.getData());
                                comboStoreRecord.commit();
                            } else {
                                comboStore.add(record);
                            }
                            enableCategorieGridButtons(grid.getSelectionModel().getSelection().length > 0);
                        }
                    });
                },
                failure: function (result, request) {
                    createErrorWindow(result);
                }
            });
        }
    },

    refreshCategorieGrid: function (button, clickEvent, options) {
        this.getCategorieStoreStore().loadPage(1, {
            params: {
                limit: categoriiPerPage
            },
            callback: function(records, operation, success) {
                if (!success) {
                    this.getCategorieStoreStore().removeAll();
                }
            }
        });
    },

    showMenu: function (panel, event, options) {
        this.configMenu(event);
    },

    itemContextMenu: function (xx, record, item, index, e, eOpts) {
        this.configMenu(e);
    },
    configMenu: function (event) {
        event.stopEvent();
        categorieMenu.showAt(event.getXY());
        categorieMenu.controller = this;
        var categoriegrid = Ext.ComponentQuery.query('categoriegrid')[0];
        categoriegrid.categorieMenu = categorieMenu;
        var hasSelection = categoriegrid.getSelectionModel().hasSelection();
        categorieMenu.items.get('modCategorie').setDisabled(!hasSelection);
        categorieMenu.items.get('delCategorie').setDisabled(!hasSelection);
    }
});

var categorieMenu = Ext.create('Ext.menu.Menu', {
    items: [
        Ext.create('Ext.Action', {
            iconCls: 'icon-add',
            text: 'Adauga categorie',
            id: 'addCategorie',
            handler: function (widget, event) {
                categorieMenu.controller.addCategorie();
            }
        }),
        Ext.create('Ext.Action', {
            iconCls: 'icon-mod',
            text: 'Modifica categorie',
            id: 'modCategorie',
            handler: function (widget, event) {
                categorieMenu.controller.modCategorie();
            }
        }),
        Ext.create('Ext.Action', {
            iconCls: 'icon-delete',
            text: 'Sterge categorie',
            id: 'delCategorie',
            handler: function (widget, event) {
                categorieMenu.controller.delCategorie();
            }
        })
    ]
});
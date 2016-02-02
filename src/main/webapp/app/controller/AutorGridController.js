Ext.define('BM.controller.AutorGridController', {
    extend: 'Ext.app.Controller',
    requires: [
        'Ext.window.MessageBox'
    ],
    stores: [
        'AutorStore'
    ],

    views: [
        'autor.AutorWindow',
        'ErrorWindow'
    ],

    init: function () {
        this.control({
            'autorgrid': {
                selectionchange: this.changeselectionAutor,
                celldblclick: this.celldblclickAutor,
                render: this.refreshAutorGrid
            },
            'autorgrid button[action=add-autor]': {
                click: this.addAutor
            },
            'autorgrid button[action=mod-autor]': {
                click: this.modAutor
            },
            'autorgrid button[action=del-autor]': {
                click: this.delAutor
            },
            'autorgrid button[action=refresh-autor]': {
                click: this.refreshAutorGrid
            },
            'autorwindow button[itemId=saveBtn]': {
                click: this.saveAutor
            },
            'autorwindow button[itemId=cancelBtn]': {
                click: this.closeAutorWindow
            }
        });
    },

    changeselectionAutor: function (selModel, selected, eOpts) {
        enableAutorGridButtons(selected.length > 0);
    },

    celldblclickAutor: function (grid, td, cellIndex, record, tr, rowIndex, e) {
        var modButton = Ext.ComponentQuery.query('autorgrid button[action=mod-autor]')[0];
        this.modAutor(modButton);
    },

    addAutor: function (button, clickEvent, options) {
        var window = Ext.widget('autorwindow');
        window.show();
    },

    modAutor: function (button, clickEvent, options) {
        var grid = button.up('autorgrid');
        var selectionModel = grid.getSelectionModel();
        if (!selectionModel.hasSelection) {
            Ext.Msg.show({
                title: 'Autor neselectat',
                msg: 'Selectati un autor',
                width: 300,
                buttons: Ext.Msg.OK,
                icon: Ext.window.MessageBox.WARNING
            });
            return;
        }
        var selectedAutor = selectionModel.getSelection()[0];
        var idAutor = selectedAutor.get('autorId');
        BM.model.AutorModel.load(idAutor, {
            scope: this,
            failure: function (record, operation) {
                grid.getStore().remove(selection);
                var error = {
                    windowTitle: 'Error loading autor with id [' + operation.id + ']',
                    httpStatus: operation.error.status + ' (' + operation.error.statusText + ')',
                    errorMessage: operation.error.status + ' (' + operation.error.statusText + ')',
                    method: operation.request.method,
                    url: operation.request.url
                };
                createGenericErrorWindow(error);
            },
            success: function (record, operation) {
                var window = Ext.widget('autorwindow');
                var autorForm = window.down('form[itemId=autorform]');
                autorForm.loadRecord(selectedAutor);
                window.show();
            }
        });
    },

    delAutor: function (button) {
        Ext.MessageBox.confirm('Confirmare', 'Sunteti sigur?', this.deleteAutor);
    },

    deleteAutor: function (button) {
        if (button == 'yes') {
            var autorgrid = Ext.ComponentQuery.query('autorgrid')[0];
            var selectionModel = autorgrid.getSelectionModel();
            if (!selectionModel.hasSelection) {
                Ext.Msg.show({
                    title: 'Autor neselectat',
                    msg: 'Selectati un autor',
                    width: 300,
                    buttons: Ext.Msg.OK,
                    icon: Ext.window.MessageBox.WARNING
                });
                return;
            }
            var selectedAutor = selectionModel.getSelection()[0];
            Ext.Ajax.request({
                url: 'autor/' + selectedAutor.get('autorId'),
                method: 'DELETE',
                scope: this,
                success: function (result, request) {
                    enableAutorGridButtons(false);
                    autorgrid.getStore().remove(selectedAutor);
                },
                failure: function (result, request) {
                    createErrorWindow(result);
                }
            });
        }
    },

    closeAutorWindow: function (button, clickEvent, options) {
        var window = button.up('autorwindow');
        window.close();
    },

    saveAutor: function (button, clickEvent, options) {
        var form = button.up('autorwindow').down('form[itemId=autorform]');
        var autorId = form.down('hidden[name=autorId]').getValue();
        var isAdd = Ext.isEmpty(autorId);
        if (form.isValid()) {
            Ext.Ajax.request({
                url: 'autor',
                method: isAdd ? 'POST' : 'PUT',
                headers: {'Content-Type': 'application/json'},
                params: Ext.JSON.encode(form.getValues()),
                success: function (response) {
                    button.up('autorwindow').close();
                    enableAutorGridButtons(false);
                    var grid = Ext.ComponentQuery.query('autorgrid')[0];
                    var idAutor = JSON.parse(response.responseText)['autorId'];
                    BM.model.AutorModel.load(idAutor, {
                        scope: this,
                        failure: function (record, operation) {
                            var error = {
                                windowTitle: 'Error loading autor with id [' + operation.id + ']',
                                httpStatus: operation.error.status + ' (' + operation.error.statusText + ')',
                                errorMessage: operation.error.status + ' (' + operation.error.statusText + ')',
                                method: operation.request.method,
                                url: operation.request.url
                            };
                            createGenericErrorWindow(error);
                        },
                        success: function (record, operation) {
                            var storeRecord = grid.getStore().getById(idAutor);
                            if (storeRecord) {
                                storeRecord.set(record.getData());
                                storeRecord.commit();
                            } else {
                                grid.getStore().add(record);
                            }
                            enableAutorGridButtons(grid.getSelectionModel().getSelection().length > 0);
                        }
                    });
                },
                failure: function (result, request) {
                    createErrorWindow(result);
                }
            });
        }
    },

    refreshAutorGrid: function (button, clickEvent, options) {
        this.getAutorStoreStore().loadPage(1, {
            params: {
                start: 0,
                limit: autoriPerPage
            },
            callback: function(records, operation, success) {
                if (!success) {
                    this.getAutorStoreStore().removeAll();
                }
            }
        });
    }
});
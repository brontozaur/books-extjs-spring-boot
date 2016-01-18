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

            init: function() {
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

            changeselectionAutor: function(selModel, selected, eOpts) {
                enablebuttonsAutor(selected.length > 0);
            },

            celldblclickAutor: function(grid, td, cellIndex, record, tr, rowIndex, e) {
                var modButton = Ext.ComponentQuery.query('autorgrid button[action=mod-autor]')[0];
                this.modAutor(modButton);
            },

            addAutor: function(button, clickEvent, options) {
                var window = Ext.widget('autorwindow');
                window.show();
            },

            modAutor: function(button, clickEvent, options) {
                var delButton = Ext.ComponentQuery.query('autorgrid button[action=del-autor]')[0];
                delButton.disable();
                var window = Ext.widget('autorwindow');
                var selectionModel = button.up('autorgrid').getSelectionModel();
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
                var bookForm = window.down('form[itemId=autorform]');
                bookForm.loadRecord(selectedAutor);
                window.show();
            },

            delAutor: function(button) {
                Ext.MessageBox.confirm('Confirmare', 'Sunteti sigur?', this.deleteAutor);
            },

            deleteAutor: function(button) {
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
                                url: 'books',
                                method: 'POST',
                                params: {
                                    event: 'del-autor',
                                    autorId: selectedAutor.get('autorId')
                                },
                                scope: this,
                                success: function(result, request) {
                                    enablebuttonsAutor(false);
                                    Ext.ComponentQuery.query('autorgrid')[0].getStore().load();
                                },
                                failure: function(result, request) {
                                    createErrorWindow(result);
                                }
                            });
                }
            },

            closeAutorWindow: function(button, clickEvent, options) {
                var window = button.up('autorwindow');
                window.close();
            },

            saveAutor: function(button, clickEvent, options) {
                var form = button.up('autorwindow').down('form[itemId=autorform]');
                if (form.isValid()) {
                    form.submit({
                                url: 'books',
                                method: 'POST',
                                params: {
                                    event: 'save-autor',
                                    autorId: form.down('hidden[name=autorId]').getValue(),
                                    nume: form.down('textfield[name=nume]').getValue()
                                },
                                success: function(form, action) {
                                    button.up('autorwindow').close();
                                    enablebuttonsAutor(false);
                                    Ext.ComponentQuery.query('autorgrid')[0].getStore().load();
                                },

                                failure: function(form, action) {
                                    createFormErrorWindow(action);
                                }
                            });
                }
            },

            refreshAutorGrid: function(button, clickEvent, options) {
                this.getAutorStoreStore().reload();
            }
        });
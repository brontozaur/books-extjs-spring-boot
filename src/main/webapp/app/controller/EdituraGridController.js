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

            init: function() {
                this.control({
                            'edituragrid': {
                                selectionchange: this.changeselectionEditura,
                                celldblclick: this.celldblclickEditura,
                                render: this.refreshEdituraGrid
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

            changeselectionEditura: function(selModel, selected, eOpts) {
                enablebuttonsEditura(selected.length > 0);
            },

            celldblclickEditura: function(grid, td, cellIndex, record, tr, rowIndex, e) {
                var modButton = Ext.ComponentQuery.query('edituragrid button[action=mod-editura]')[0];
                this.modEditura(modButton);
            },

            addEditura: function(button, clickEvent, options) {
                var window = Ext.widget('editurawindow');
                window.show();
            },

            modEditura: function(button, clickEvent, options) {
                var delButton = Ext.ComponentQuery.query('edituragrid button[action=del-editura]')[0];
                delButton.disable();
                var window = Ext.widget('editurawindow');
                var selectionModel = button.up('edituragrid').getSelectionModel();
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
                var bookForm = window.down('form[itemId=edituraform]');
                bookForm.loadRecord(selectedEditura);
                window.show();
            },

            delEditura: function(button) {
                Ext.MessageBox.confirm('Confirmare', 'Sunteti sigur?', this.deleteEditura);
            },

            deleteEditura: function(btn) {
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
                                url: 'books',
                                method: 'POST',
                                params: {
                                    event: 'del-editura',
                                    idEditura: selectedEditura.get('idEditura')
                                },
                                scope: this,
                                success: function(result, request) {
                                    enablebuttonsEditura(false);
                                    Ext.ComponentQuery.query('edituragrid')[0].getStore().load();
                                },
                                failure: function(result, request) {
                                    createErrorWindow(result);
                                }
                            });
                }
            },

            closeEdituraWindow: function(button, clickEvent, options) {
                var window = button.up('editurawindow');
                window.close();
            },

            saveEditura: function(button, clickEvent, options) {
                var form = button.up('editurawindow').down('form[itemId=edituraform]');
                if (form.isValid()) {
                    form.submit({
                                url: 'books',
                                method: 'POST',
                                params: {
                                    event: 'save-editura',
                                    idEditura: form.down('hidden[name=idEditura]').getValue(),
                                    title: form.down('textfield[name=numeEditura]').getValue()
                                },
                                success: function(form, action) {
                                    button.up('editurawindow').close();
                                    enablebuttonsEditura(false);
                                    Ext.ComponentQuery.query('edituragrid')[0].getStore().load();
                                },

                                failure: function(form, action) {
                                    createFormErrorWindow(action);
                                }
                            });
                }
            },

            refreshEdituraGrid: function(button, clickEvent, options) {
                this.getEdituraStoreStore().reload();
            }
        });
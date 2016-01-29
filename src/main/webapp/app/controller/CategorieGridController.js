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

            init: function() {
                this.control({
                            'categoriegrid': {
                                selectionchange: this.changeselectionCategorie,
                                celldblclick: this.celldblclickCategorie,
                                render: this.refreshCategorieGrid
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

            changeselectionCategorie: function(selModel, selected, eOpts) {
                enablebuttonsCategorie(selected.length > 0);
            },

            celldblclickCategorie: function(grid, td, cellIndex, record, tr, rowIndex, e) {
                var modButton = Ext.ComponentQuery.query('categoriegrid button[action=mod-categorie]')[0];
                this.modCategorie(modButton);
            },

            addCategorie: function(button, clickEvent, options) {
                var window = Ext.widget('categoriewindow');
                window.show();
            },

            modCategorie: function(button, clickEvent, options) {
                var delButton = Ext.ComponentQuery.query('categoriegrid button[action=del-categorie]')[0];
                delButton.disable();
                var window = Ext.widget('categoriewindow');
                var selectionModel = button.up('categoriegrid').getSelectionModel();
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
                var categorieForm = window.down('form[itemId=categorieform]');
                categorieForm.loadRecord(selectedCategorie);
                window.show();
            },

            delCategorie: function(button) {
                Ext.MessageBox.confirm('Confirmare', 'Sunteti sigur?', this.deleteCategorie);
            },

            deleteCategorie: function(btn) {
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
                                url: '/categorie/'+ selectedCategorie.get('idCategorie'),
                                method: 'DELETE',
                                scope: this,
                                success: function(result, request) {
                                    enablebuttonsCategorie(false);
                                    Ext.ComponentQuery.query('categoriegrid')[0].getStore().load();
                                },
                                failure: function(result, request) {
                                    createErrorWindow(result);
                                }
                            });
                }
            },

            closeCategorieWindow: function(button, clickEvent, options) {
                var window = button.up('categoriewindow');
                window.close();
            },

            saveCategorie: function(button, clickEvent, options) {
                var form = button.up('categoriewindow').down('form[itemId=categorieform]');
                var idCategorie = form.down('hidden[name=idCategorie]').getValue();
                var isAdd = Ext.isEmpty(idCategorie);
                if (form.isValid()) {
                    form.submit({
                                url: (isAdd ? '/categorie': '/categorie/'+idCategorie),
                                method: isAdd ? 'POST' : 'PUT',
                                params: {
                                    title: form.down('textfield[name=numeCategorie]').getValue()
                                },
                                success: function(form, action) {
                                    button.up('categoriewindow').close();
                                    enablebuttonsCategorie(false);
                                    Ext.ComponentQuery.query('categoriegrid')[0].getStore().load();
                                },

                                failure: function(form, action) {
                                    createErrorWindow(action.response);
                                }
                            });
                }
            },

            refreshCategorieGrid: function(button, clickEvent, options) {
                this.getCategorieStoreStore().load();
            }
        });
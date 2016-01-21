Ext.define('BM.controller.BookWindowController', {
            extend: 'Ext.app.Controller',
            stores: [
                'BookStore',
                'AutorStore',
                'EdituraStore'
            ],

            views: [
                'book.BookWindow',
                'editura.EdituraWindow',
                'autor.AutorWindow',
                'categorie.CategorieWindow',
                'ErrorWindow'
            ],

            init: function() {
                this.control({
                            'bookwindow button[itemId=saveBtn]': {
                                click: this.saveBook
                            },
                            'bookwindow button[itemId=cancelBtn]': {
                                click: this.closeWindow
                            },
                            'bookwindow button[itemId=addAutor]': {
                                click: function(button, clickEvent, options) {
                                    var window = Ext.widget('autorwindow');
                                    window.show();
                                }
                            },
                            'bookwindow button[itemId=addEditura]': {
                                click: function(button, clickEvent, options) {
                                    var window = Ext.widget('editurawindow');
                                    window.show();
                                }
                            },
                            'bookwindow button[itemId=addCategorie]': {
                                click: function(button, clickEvent, options) {
                                    var window = Ext.widget('categoriewindow');
                                    window.show();
                                }
                            },
                            'bookwindow filefield[name=frontCoverUpload]': {
                                change: this.uploadFrontCover
                            },
                            'bookwindow button[itemId=removeFrontUpload]': {
                                click: this.deleteFrontCover
                            },
                            'bookwindow filefield[name=backCoverUpload]': {
                                change: this.uploadBackCover
                            },
                            'bookwindow button[itemId=removeBackUpload]': {
                                click: this.deleteBackCover
                            },
                            'bookwindow button[itemId=frontCoverButton]': {
                                click: this.front
                            },
                            'bookwindow button[itemId=backCoverButton]': {
                                click: this.back
                            }
                        });
            },

            saveBook: function(button, clickEvent, options) {
                var form = button.up('bookwindow').down('form[itemId=bookform]');
                var me = this;
                var bookId = form.down('hidden[name=bookId]').getValue();
                var isAdd = Ext.isEmpty(bookId);
                //TODO validare pt editura si autor neselectati (scrii in combo, dar nu ai selectie)
                if (form.isValid()) {
                    Ext.Ajax.request({
                        url: (isAdd ? 'book' : 'book/' + bookId),
                        method: isAdd ? 'POST' : 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        params : Ext.JSON.encode(form.getValues()),
                        isUpload: false,
                        scope: this,
                        success: function(response) {
                            me.closeWindow(button);
                            clearInfoAreaFields();
                            enablebuttons(false);
                            var grid = Ext.ComponentQuery.query('booksgrid')[0];
                            grid.getStore().load();
                        },
                        failure: function(result, request) {
                            createErrorWindow(result);
                        }
                    });
                }
                // Requestul de mai sus e echivalent cu urmatorul:
              /*
               $.ajax({
                    url: (isAdd ? 'book' : 'book/' + bookId),
                    contentType: "application/json",
                    dataType: "json",
                    type: isAdd ? 'post' : 'put',
                    data: JSON.stringify(form.getValues()),
                    success: function(response) {
                        alert ('Cartea a fost salvata cu succes!');
                    },
                    error: function(xhr) {
                        alert ('A intervenit o eroare.');
                    }
                });
                */
            },

            closeWindow: function(button, clickEvent, options) {
                var window = button.up('bookwindow');
                window.close();
            },

            uploadFrontCover: function(fileUploadField, value, eOpts) {
                var form = fileUploadField.up('bookwindow').down('form[itemId=frontUploadform]');
                var bookForm = fileUploadField.up('bookwindow').down('form[itemId=bookform]');
                if (form.isValid()) {
                    form.submit({
                                url: 'books',
                                method: 'POST',
                                params: {
                                    event: 'image-loader',
                                    isUpload: true,
                                    isFrontCover: 'true',
                                    bookId: bookForm.down('hidden[name=bookId]').getValue()
                                },
                                success: function(form, action) {
                                    var response = Ext.JSON.decode(action.response.responseText);
                                    var imageCanvas = Ext.ComponentQuery.query('image[itemId=frontCoverPreview]')[0];
                                    imageCanvas.setSrc("/books/data/" + response.fileName);
                                },

                                failure: function(form, action) {
                                    createFormErrorWindow(action);
                                }
                            });
                }
            },

            deleteFrontCover: function(button, e, eOpts) {
                var bookForm = button.up('bookwindow').down('form[itemId=bookform]');
                Ext.Ajax.request({
                    url: 'books',
                    method: 'POST',
                    params: {
                        event: 'del-upload',
                        bookId: bookForm.down('hidden[name=bookId]').getValue(),
                        isFrontCover: true
                    },
                    scope: this,
                    success: function(result, request) {
                        var imageCanvas = Ext.ComponentQuery.query('image[itemId=backCoverPreview]')[0];
                        imageCanvas.setSrc(null);
                    },
                    failure: function(result, request) {
                        createErrorWindow(result);
                    }
                });
            },

            uploadBackCover: function(fileUploadField, value, eOpts) {
                var form = fileUploadField.up('bookwindow').down('form[itemId=backUploadform]');
                var bookForm = fileUploadField.up('bookwindow').down('form[itemId=bookform]');
                if (form.isValid()) {
                    form.submit({
                                url: 'books',
                                method: 'POST',
                                params: {
                                    event: 'image-loader',
                                    isUpload: true,
                                    isFrontCover: 'false',
                                    bookId: bookForm.down('hidden[name=bookId]').getValue()
                                },
                                success: function(form, action) {
                                    var response = Ext.JSON.decode(action.response.responseText);
                                    var imageCanvas = Ext.ComponentQuery.query('image[itemId=backCoverPreview]')[0];
                                    imageCanvas.setSrc("/books/data/" + response.fileName);
                                },

                                failure: function(form, action) {
                                    createFormErrorWindow(action);
                                }
                            });
                }
            },

            deleteBackCover: function(button, e, eOpts) {
                var bookForm = button.up('bookwindow').down('form[itemId=bookform]');
                Ext.Ajax.request({
                    url: 'books',
                    method: 'POST',
                    params: {
                        event: 'del-upload',
                        bookId: bookForm.down('hidden[name=bookId]').getValue(),
                        isFrontCover: false
                    },
                    scope: this,
                    success: function(result, request) {
                        var imageCanvas = Ext.ComponentQuery.query('image[itemId=backCoverPreview]')[0];
                        imageCanvas.setSrc(null);
                    },
                    failure: function(result, request) {
                        createErrorWindow(result);
                    }
                });
            },

            back: function(button, e, eOpts) {
                var panel = button.up('toolbar[itemId=coversToolbar]').up('panel[itemId=cardLayoutPanel]');
                panel.getLayout().setActiveItem('backUploadform');
            },

            front: function(button, e, eOpts) {
                var panel = button.up('toolbar[itemId=coversToolbar]').up('panel[itemId=cardLayoutPanel]');
                panel.getLayout().setActiveItem('frontUploadform');
            }
        });
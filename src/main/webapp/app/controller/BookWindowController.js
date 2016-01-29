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
                        url: 'cover/front',
                        method: 'POST',
                        headers: {'Content-type':'multipart/form-data'},
                        params: {
                            bookId: bookForm.down('hidden[name=bookId]').getValue()
                        },
                        success: function(form, action) {
                            var response = Ext.JSON.decode(action.response.responseText);
                            var imageCanvas = Ext.ComponentQuery.query('image[itemId=frontCoverPreview]')[0];
                            imageCanvas.setSrc(response.fileName);
                            //hiden field to be submitted with form
                            bookForm.down('hidden[name=frontCoverName]').setValue(response.fileName);
                        },

                        failure: function(form, action) {
                            createErrorWindow(action.response);
                        }
                    });
                }
            },

            deleteFrontCover: function(button, e, eOpts) {
                var imageCanvas = Ext.ComponentQuery.query('image[itemId=frontCoverPreview]')[0];
                this.deleteUpload(imageCanvas, true);
            },

            uploadBackCover: function(fileUploadField, value, eOpts) {
                var form = fileUploadField.up('bookwindow').down('form[itemId=backUploadform]');
                var bookForm = fileUploadField.up('bookwindow').down('form[itemId=bookform]');
                if (form.isValid()) {
                    form.submit({
                        url: 'cover/back',
                        method: 'POST',
                        headers: {'Content-type':'multipart/form-data'},
                        params: {
                            bookId: bookForm.down('hidden[name=bookId]').getValue()
                        },
                        success: function(form, action) {
                            var response = Ext.JSON.decode(action.response.responseText);
                            var imageCanvas = Ext.ComponentQuery.query('image[itemId=backCoverPreview]')[0];
                            imageCanvas.setSrc(response.fileName);
                            //hiden field to be submitted with form
                            bookForm.down('hidden[name=backCoverName]').setValue(response.fileName);
                        },

                        failure: function(form, action) {
                            createErrorWindow(action.response);
                        }
                    });
                }
            },

            deleteBackCover: function(button, e, eOpts) {
                var imageCanvas = Ext.ComponentQuery.query('image[itemId=backCoverPreview]')[0];
                this.deleteUpload(imageCanvas, false);
            },

            deleteUpload: function(imageCanvas, isFrontCover) {
                if (!Ext.isEmpty(imageCanvas.src)) {
                    Ext.Ajax.request({
                        url: '/cover',
                        method: 'DELETE',
                        //de forma /covers/1front_whatever.jpg?time=Sat%20Jan%2023%2001:05:44%20EET%202016
                        params: {
                            imageName: JSON.stringify(imageCanvas.src)
                        },
                        /* JSON data to use as the post. Note: This will be used instead of params for the post data.
                            Any params will be appended to the URL.
                        */
                        jsonData: true,
                        scope: this,
                        success: function (result, request) {
                            imageCanvas.setSrc(null);
                            var bookForm = Ext.ComponentQuery.query('form[itemId=bookform]')[0];
                            var hiddenField;
                            if (isFrontCover) {
                                hiddenField = bookForm.down('hidden[name=frontCoverName]');
                            } else {
                                hiddenField = bookForm.down('hidden[name=backCoverName]')
                            }
                            hiddenField.setValue(null);
                        },
                        failure: function (result, request) {
                            createErrorWindow(result);
                        }
                    });
                }
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
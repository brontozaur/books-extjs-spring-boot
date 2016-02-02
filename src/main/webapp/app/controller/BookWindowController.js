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

    init: function () {
        this.control({
            'bookwindow button[itemId=saveBtn]': {
                click: this.saveBook
            },
            'bookwindow button[itemId=cancelBtn]': {
                click: this.closeWindow
            },
            'bookwindow button[itemId=addAutor]': {
                click: function (button, clickEvent, options) {
                    var window = Ext.widget('autorwindow');
                    window.show();
                }
            },
            'bookwindow button[itemId=addEditura]': {
                click: function (button, clickEvent, options) {
                    var window = Ext.widget('editurawindow');
                    window.show();
                }
            },
            'bookwindow button[itemId=addCategorie]': {
                click: function (button, clickEvent, options) {
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

    saveBook: function (button, clickEvent, options) {
        var form = button.up('bookwindow').down('form[itemId=bookform]');
        var me = this;
        var bookId = form.down('hidden[name=bookId]').getValue();
        var isAdd = Ext.isEmpty(bookId);
        //TODO validare pt editura si autor neselectati (scrii in combo, dar nu ai selectie)
        if (form.isValid()) {
            Ext.Ajax.request({
                url: (isAdd ? 'book' : 'book/' + bookId),
                method: isAdd ? 'POST' : 'PUT',
                headers: {'Content-Type': 'application/json'},
                params: Ext.JSON.encode(form.getValues()),
                isUpload: false,
                scope: this,
                success: function (response) {
                    me.closeWindow(button);
                    clearInfoAreaFields();
                    enableBookGridButtons(false);
                    var grid = Ext.ComponentQuery.query('booksgrid')[0];
                    var bookId = JSON.parse(response.responseText)['bookId'];
                    BM.model.BookModel.load(bookId, {
                        scope: this,
                        failure: function (record, operation) {
                            var error = {
                                windowTitle: 'Error loading book with record [' + operation.id + ']',
                                httpStatus: operation.error.status + ' (' + operation.error.statusText + ')',
                                errorMessage: operation.error.status + ' (' + operation.error.statusText + ')',
                                method: operation.request.method,
                                url: operation.request.url
                            };
                            createGenericErrorWindow(error);
                        },
                        success: function (record, operation) {
                            var storeRecord = grid.getStore().getById(bookId);
                            if (storeRecord) {
                                storeRecord.set(record.getData());
                                storeRecord.commit();
                            } else {
                                grid.getStore().add(record);
                            }
                            enableBookGridButtons(grid.getSelectionModel().getSelection().length > 0);
                        }
                    });
                },
                failure: function (result, request) {
                    createErrorWindow(result);
                }
            });
        }
    },

    closeWindow: function (button, clickEvent, options) {
        var window = button.up('bookwindow');
        window.close();
    },

    uploadFrontCover: function (fileUploadField, value, eOpts) {
        var form = fileUploadField.up('bookwindow').down('form[itemId=frontUploadform]');
        var bookForm = fileUploadField.up('bookwindow').down('form[itemId=bookform]');
        if (form.isValid()) {
            form.submit({
                url: 'cover/front',
                method: 'POST',
                headers: {'Content-type': 'multipart/form-data'},
                success: function (form, action) {
                    var response = Ext.JSON.decode(action.response.responseText);
                    var imageCanvas = Ext.ComponentQuery.query('image[itemId=frontCoverPreview]')[0];
                    imageCanvas.setSrc('data:image/jpeg;base64,' + response.imageData);
                    //hiden field to be submitted with book form
                    bookForm.down('hidden[name=frontCoverData]').setValue('data:image/jpeg;base64,' + response.imageData);
                },

                failure: function (form, action) {
                    createErrorWindow(action.response);
                }
            });
        }
    },

    deleteFrontCover: function (button, e, eOpts) {
        var imageCanvas = Ext.ComponentQuery.query('image[itemId=frontCoverPreview]')[0];
        imageCanvas.setSrc(null);

        var bookForm = imageCanvas.up('bookwindow').down('form[itemId=bookform]');
        bookForm.down('hidden[name=frontCoverData]').setValue(null);
    },

    uploadBackCover: function (fileUploadField, value, eOpts) {
        var form = fileUploadField.up('bookwindow').down('form[itemId=backUploadform]');
        var bookForm = fileUploadField.up('bookwindow').down('form[itemId=bookform]');
        if (form.isValid()) {
            form.submit({
                url: 'cover/back',
                method: 'POST',
                headers: {'Content-type': 'multipart/form-data'},
                success: function (form, action) {
                    var response = Ext.JSON.decode(action.response.responseText);
                    var imageCanvas = Ext.ComponentQuery.query('image[itemId=backCoverPreview]')[0];
                    imageCanvas.setSrc('data:image/jpeg;base64,' + response.imageData);
                    //hiden field to be submitted with book form
                    bookForm.down('hidden[name=backCoverData]').setValue('data:image/jpeg;base64,' + response.imageData);
                },

                failure: function (form, action) {
                    createErrorWindow(action.response);
                }
            });
        }
    },

    deleteBackCover: function (button, e, eOpts) {
        var imageCanvas = Ext.ComponentQuery.query('image[itemId=backCoverPreview]')[0];
        imageCanvas.setSrc(null);

        var bookForm = imageCanvas.up('bookwindow').down('form[itemId=bookform]');
        bookForm.down('hidden[name=backCoverData]').setValue(null);
    },

    back: function (button, e, eOpts) {
        var panel = button.up('toolbar[itemId=coversToolbar]').up('panel[itemId=cardLayoutPanel]');
        panel.getLayout().setActiveItem('backUploadform');
    },

    front: function (button, e, eOpts) {
        var panel = button.up('toolbar[itemId=coversToolbar]').up('panel[itemId=cardLayoutPanel]');
        panel.getLayout().setActiveItem('frontUploadform');
    }
});
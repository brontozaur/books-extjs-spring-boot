Ext.define('BM.controller.BooksGridController', {
    extend: 'Ext.app.Controller',
    stores: [
        'BookStore'
    ],

    views: [
        'book.BooksGrid',
        'book.BookWindow'
    ],

    init: function () {
        this.control({
            'booksgrid': {
                selectionchange: this.changeSelection,
                celldblclick: this.cellDoubleClick,
                itemkeydown: this.handleKeyPress,
                itemcontextmenu: this.itemContextMenu,
                containercontextmenu: this.showMenu
            },
            'booksgrid button[action=add-book]': {
                click: this.addBook
            },
            'booksgrid button[action=mod-book]': {
                click: this.modBook
            },
            'booksgrid button[action=del-book]': {
                click: this.delBook
            },
            'booksgrid textfield[name=searchField]': {
                keydown: this.handleKeyDown
            },
            'booksgrid button[action=search]': {
                click: this.handleClick
            },
            /**
             * This event is used to append listeners to store's proxy when paging controls are used
             * (previous, next, moveLast or moveFirst). Make sure that store has the required parameters set:
             *  - filterValue: (first letter of the book, autor id, editura id or root name (ignored in this case)
             *  - searchType: see @BookController: grid, treeBooks, treeEdituri or treeAutori
             */
            'booksgrid pagingtoolbar[itemId=booksPaging]' : {
                'beforechange': function (toolbar, page, options) {
                    var store = Ext.ComponentQuery.query('booksgrid')[0].getStore();
                    appendBooksStoreExtraParameters(store);
                }
            }
        });
    },

    changeSelection: function (selModel, selected, eOpts) {
        if (selected.length > 0) {
            enableBookGridButtons(true);
            fillInfoArea(selected[0]);
        }
    },

    cellDoubleClick: function (grid, td, cellIndex, record, tr, rowIndex, e) {
        var modButton = Ext.ComponentQuery.query('booksgrid button[action=mod-book]')[0];
        this.modBook(modButton);
    },

    handleKeyPress: function (grid, record, item, index, event, eOpts) {
        if (event.keyCode == Ext.EventObject.DELETE && grid.getSelectionModel().hasSelection()) {
            this.delBook();
        }
    },

    addBook: function (button, clickEvent, options) {
        var window = Ext.widget('bookwindow');
        window.show();
    },

    modBook: function (button, clickEvent, options) {
        var grid = Ext.ComponentQuery.query('booksgrid')[0];
        var selectionModel = grid.getSelectionModel();
        if (!selectionModel.hasSelection) {
            Ext.Msg.show({
                title: 'Carte neselectata',
                msg: 'Selectati o carte',
                width: 300,
                buttons: Ext.Msg.OK,
                icon: Ext.window.MessageBox.WARNING
            });
            return;
        }
        var selection = selectionModel.getSelection()[0];
        var bookId = selection.get('bookId');
        BM.model.BookModel.load(bookId, {
            scope: this,
            failure: function (record, operation) {
                grid.getStore().remove(selection);
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
                var window = Ext.widget('bookwindow');
                var bookForm = window.down('form[itemId=bookform]');
                bookForm.loadRecord(record);
                var coversComponent = window.down('component[itemId=cardLayoutPanel]');
                if (!Ext.isEmpty(record.get('frontCover'))) {
                    var frontCoverUploadForm = coversComponent.down('form[itemId=frontUploadform]');
                    var uploadForm = frontCoverUploadForm.down('image[itemId=frontCoverPreview]');
                    uploadForm.setSrc('data:image/jpeg;base64,' + record.get('frontCover'));
                    bookForm.down('hidden[name=frontCoverData]').setValue(uploadForm.src);
                }
                if (!Ext.isEmpty(record.get('backCover'))) {
                    var backCoverUploadForm = coversComponent.down('form[itemId=backUploadform]');
                    var uploadForm = backCoverUploadForm.down('image[itemId=backCoverPreview]');
                    uploadForm.setSrc('data:image/jpeg;base64,' + record.get('backCover'));
                    bookForm.down('hidden[name=backCoverData]').setValue(uploadForm.src);
                }
                window.show();
            },
            callback: function (record, operation, success) {
                //nothing to do here
            }
        });
    },

    delBook: function (button, clickEvent, options) {
        Ext.MessageBox.confirm('Confirmare stergere', 'Sunteti sigur ca doriti sa stergeti cartea selectata?', this.deleteBook);
    },

    deleteBook: function (btn) {
        if (btn == 'yes') {
            var booksGrid = Ext.ComponentQuery.query('booksgrid')[0];
            var selectionModel = booksGrid.getSelectionModel();
            if (!selectionModel.hasSelection) {
                Ext.Msg.show({
                    title: 'Carte neselectata',
                    msg: 'Selectati o carte',
                    width: 300,
                    buttons: Ext.Msg.OK,
                    icon: Ext.window.MessageBox.WARNING
                });
                return;
            }
            var selectedBook = selectionModel.getSelection()[0];
            Ext.Ajax.request({
                url: '/book/' + selectedBook.get('bookId'),
                method: 'DELETE',
                scope: this,
                success: function (result, request) {
                    clearInfoAreaFields();
                    enableBookGridButtons(false);
                    booksGrid.getStore().remove(selectedBook);
                },
                failure: function (result, request) {
                    createErrorWindow(result);
                }
            });
        }
    },

    handleKeyDown: function (textfield, event, opts) {
        if (event.keyCode == Ext.EventObject.RETURN) {
            var grid = Ext.ComponentQuery.query('booksgrid')[0];
            var store = grid.getStore();
            this.searchBooks(store, textfield.getValue());
        }
    },

    handleClick: function (button, event, opts) {
        var parent = button.up('toolbar');
        var searchField = parent.down('textfield[name=searchField]');
        var grid = Ext.ComponentQuery.query('booksgrid')[0];
        var store = grid.getStore();
        this.searchBooks(store, searchField.getValue());
    },

    searchBooks: function (store, filteredValue) {
        store.clearFilter(true);
        store.filter([
            {
                filterFn: function (record) {
                    if (Ext.isEmpty(filteredValue)) {
                        return true;
                    }
                    var numeAutor = record.get('authorName');
                    var title = record.get('title');
                    if (Ext.isEmpty(numeAutor)) {
                        numeAutor = "";
                    }
                    if (Ext.isEmpty(title)) {
                        title = "";
                    }
                    return numeAutor.indexOf(filteredValue) > -1 || title.indexOf(filteredValue) > -1;
                }
            }
        ]);
    },

    showMenu: function (panel, event, options) {
        this.configMenu(event);
    },

    itemContextMenu: function (xx, record, item, index, e, eOpts) {
        this.configMenu(e);
    },
    configMenu: function (event) {
        event.stopEvent();
        booksMenu.showAt(event.getXY());
        booksMenu.controller = this;
        var booksgrid = Ext.ComponentQuery.query('booksgrid')[0];
        booksgrid.booksMenu = booksMenu;
        var hasSelection = booksgrid.getSelectionModel().hasSelection();
        booksMenu.items.get('modBook').setDisabled(!hasSelection);
        booksMenu.items.get('delBook').setDisabled(!hasSelection);
    }
});

var booksMenu = Ext.create('Ext.menu.Menu', {
    items: [
        Ext.create('Ext.Action', {
            iconCls: 'icon-add',
            text: 'Adauga carte',
            id: 'addBook',
            handler: function (widget, event) {
                booksMenu.controller.addBook();
            }
        }),
        Ext.create('Ext.Action', {
            iconCls: 'icon-mod',
            text: 'Modifica carte',
            id: 'modBook',
            handler: function (widget, event) {
                booksMenu.controller.modBook();
            }
        }),
        Ext.create('Ext.Action', {
            iconCls: 'icon-delete',
            text: 'Sterge carte',
            id: 'delBook',
            handler: function (widget, event) {
                booksMenu.controller.delBook();
            }
        })
    ]
});
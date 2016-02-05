Ext.define('BM.view.book.BooksGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.booksgrid',
    selType: 'rowmodel',
    columnLines: true,
    store: 'BookStore',
    stripeRows: true,
    viewConfig: {
        listeners: {
            refresh: function (dataview) {
                Ext.each(dataview.panel.columns, function (column) {
                    if (column.autoSizeColumn === true)
                        column.autoSize();
                })
            }
        }
    },
    dockedItems: [
        {
            xtype: 'pagingtoolbar',
            store: 'BookStore',
            itemId: 'booksPaging',
            dock: 'bottom',
            displayInfo: true,
            border: false
        },
        {
            xtype: 'toolbar',
            dock: 'top',
            border: false,
            items: [
                {
                    xtype: 'button',
                    iconCls: 'icon-add',
                    text: 'Adaugare',
                    action: 'add-book'

                },
                {
                    xtype: 'button',
                    iconCls: 'icon-mod',
                    text: 'Modificare',
                    disabled: true,
                    action: 'mod-book'
                },
                {
                    xtype: 'button',
                    iconCls: 'icon-delete',
                    text: 'Stergere',
                    disabled: true,
                    action: 'del-book'
                },
                '->',
                {
                    xtype: 'textfield',
                    name: 'searchField',
                    emptyText: 'enter search term',
                    enableKeyEvents: true
                },
                {
                    xtype: 'button',
                    iconCls: 'icon-search',
                    action: 'search',
                    text: 'Cautare'
                }
            ]
        }
    ],

    initComponent: function () {
        var me = this;
        me.columns = this.buildColumns();
        me.callParent(arguments);
        me.store.filterValue = '';
        me.store.searchType = 'grid';
        me.store.loadPage(1, {
            params: {
                limit: booksPerPage,
                filterValue: me.store.filterValue,
                searchType: me.store.searchType
            },
            callback: function (records, operation, success) {
                if (!success) {
                    me.store.removeAll();
                }
            }
        });
    },

    buildColumns: function () {
        return [
            {
                header: 'Id',
                dataIndex: 'bookId',
                autoSizeColumn: true
            },
            {
                header: 'Autor',
                dataIndex: 'authorName',
                autoSizeColumn: true,
                editor: 'textfield'
            },
            {
                header: 'Titlu',
                dataIndex: 'title',
                autoSizeColumn: true
            },
            {
                header: 'An aparitie',
                dataIndex: 'anAparitie',
                editor: 'datefield',
                renderer: Ext.util.Format.dateRenderer('Y'),
                autoSizeColumn: true
            },
            {
                header: 'Titlu original',
                dataIndex: 'originalTitle',
                autoSizeColumn: true
            },
            {
                header: 'ISBN',
                dataIndex: 'isbn',
                autoSizeColumn: true
            },
            {
                header: 'Serie',
                dataIndex: 'serie',
                autoSizeColumn: true
            },
            {
                header: 'Nr pagini',
                dataIndex: 'nrPagini',
                autoSizeColumn: true
            },
            {
                header: 'Editura',
                dataIndex: 'numeEditura',
                autoSizeColumn: true
            },
            {
                header: 'Gen',
                dataIndex: 'numeCategorie',
                autoSizeColumn: true
            },
            {
                header: 'Latime (mm)',
                dataIndex: 'width',
                autoSizeColumn: true
            },
            {
                header: 'Inaltime (mm)',
                dataIndex: 'height',
                autoSizeColumn: true
            },
            {
                xtype: 'booleancolumn',
                falseText: 'Nu',
                trueText: 'Da',
                header: 'Citita',
                dataIndex: 'citita',
                autoSizeColumn: true
            }
        ];
    }
});
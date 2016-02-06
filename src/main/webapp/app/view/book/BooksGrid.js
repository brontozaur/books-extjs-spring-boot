Ext.define('BM.view.book.BooksGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.booksgrid',
    selType: 'rowmodel',
    columnLines: true,
    store: 'BookStore',
    stripeRows: true,
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
                width: 50
            },
            {
                header: 'Autor',
                dataIndex: 'authorName',
                flex: 1,
                editor: 'textfield'
            },
            {
                header: 'Titlu',
                dataIndex: 'title',
                flex: 2
            },
            {
                header: 'An aparitie',
                dataIndex: 'anAparitie',
                editor: 'datefield',
                renderer: Ext.util.Format.dateRenderer('Y'),
                width: 50
            },
            {
                header: 'Titlu original',
                dataIndex: 'originalTitle',
                flex: 2
            },
            {
                header: 'ISBN',
                dataIndex: 'isbn',
                flex: 1
            },
            {
                header: 'Serie',
                dataIndex: 'serie',
                flex: 1
            },
            {
                header: 'Nr pagini',
                dataIndex: 'nrPagini',
                flex: 1
            },
            {
                header: 'Editura',
                dataIndex: 'numeEditura',
                flex: 1
            },
            {
                header: 'Gen',
                dataIndex: 'numeCategorie',
                flex: 1
            },
            {
                header: 'Latime (mm)',
                dataIndex: 'width',
                width: 50
            },
            {
                header: 'Inaltime (mm)',
                dataIndex: 'height',
                width: 50
            },
            {
                xtype: 'booleancolumn',
                falseText: 'Nu',
                trueText: 'Da',
                header: 'Citita',
                dataIndex: 'citita',
                width: 50
            }
        ];
    }
});
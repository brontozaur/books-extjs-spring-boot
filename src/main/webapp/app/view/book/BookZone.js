Ext.define('BM.view.book.BookZone', {
            extend: 'Ext.panel.Panel',
            alias: 'widget.bookzone',
            requires: [
                'BM.view.book.BooksGrid',
                'BM.view.book.BookInfo'
            ],
            layout: 'border',
            border: false,
            items: [
                {
                    xtype: 'booksgrid',
                    region: 'center'
                },
                {
                    xtype: 'bookinfo',
                    region: 'south',
                    collapsible: true,
                    split: true,
                    height: '40%'
                }
            ]
        });
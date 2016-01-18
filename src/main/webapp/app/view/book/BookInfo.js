Ext.define('BM.view.book.BookInfo', {
            extend: 'Ext.panel.Panel',
            alias: 'widget.bookinfo',
            bodyPadding: 20,
            title: 'Detalii carte',
            layout: 'hbox',
            items: this.buildItems()
        });

function buildItems() {
    return [
        {
            xtype: 'container',
            itemId: 'bookInfoFields',
            defaults: {
                xtype: 'displayfield',
                labelCls: 'book-info-label',
                fieldCls: 'book-info-label-value',
                hidden: true
            },
            items: [
                {
                    fieldLabel: 'Autor',
                    itemId: 'autor'
                },
                {
                    fieldLabel: 'Titlu',
                    itemId: 'title'
                },
                {
                    fieldLabel: 'Data aparitie',
                    itemId: 'data'
                },
                {
                    fieldLabel: 'Titlu original',
                    itemId: 'originalTitle'
                },
                {
                    fieldLabel: 'ISBN',
                    itemId: 'isbn'
                },
                {
                    fieldLabel: 'Citita',
                    itemId: 'citita'
                },
                {
                    fieldLabel: 'Serie',
                    itemId: 'serie'
                },
                {
                    fieldLabel: 'Nr pagini',
                    itemId: 'nrPagini'
                },
                {
                    fieldLabel: 'Dimensiuni',
                    itemId: 'dimensiuni'
                },
                {
                    fieldLabel: 'Editura',
                    itemId: 'numeEditura'
                },
                {
                    fieldLabel: 'Gen',
                    itemId: 'numeCategorie'
                },
                {
                    fieldLabel: 'Cover',
                    itemId: 'frontCover'
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'vbox',
            margin: '0 0 0 200',
            defaults: {
                hidden: true
            },
            items: [
                {
                    xtype: 'label',
                    text: 'Front cover',
                    itemId: 'frontCoverLabel',
                    baseCls: 'book-info-label-value'
                },
                {
                    xtype: 'image',
                    height: 180,
                    width: 160,
                    itemId: 'frontCoverInfo',
                    title: 'Front cover'
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'vbox',
            margin: '0 0 0 30',
            defaults: {
                hidden: true
            },
            items: [
                {
                    xtype: 'label',
                    itemId: 'backCoverLabel',
                    text: 'Back cover',
                    baseCls: 'book-info-label-value'
                },
                {
                    xtype: 'image',
                    height: 180,
                    width: 160,
                    itemId: 'backCoverInfo',
                    title: 'Back cover'
                }
            ]
        }
    ];
}
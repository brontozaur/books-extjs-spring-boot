Ext.define('BM.view.Viewport', {
    extend: 'Ext.container.Viewport',
    requires: [
        'Ext.layout.container.Border',
        'BM.view.book.BookZone',
        'BM.view.tree.TreeAutori',
        'BM.view.tree.TreeBooks',
        'BM.view.tree.TreeEditura',
        'BM.view.editura.EdituraGrid',
        'BM.view.autor.AutorGrid',
        'BM.view.categorie.CategorieGrid',
        'BM.view.tree.LeftTreeArea'
    ],
    layout: 'border',
    items: [
        {
            xtype: 'toolbar',
            region: 'north',
            height: 40,
            padding: '7 5 5 10',
            baseCls: 'app-header',
            html: 'Books Manager'
        },
        {
            xtype: 'tabpanel',
            region: 'center',
            plain: true,
            activeTab: 0,
            bodyStyle: 'border-top-style : none;',
            padding: '5 5 5 5',
            items: [
                {
                    xtype: 'panel',
                    title: 'Carti',
                    layout: 'border',
                    padding: '0 2 2 2',
                    items: [
                        {
                            xtype: 'lefttree',
                            region: 'west',
                            width: '15%'
                        },
                        {
                            region: 'center',
                            xtype: 'bookzone'
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    padding: '0 2 2 2',
                    title: 'Edituri',
                    layout: 'fit',
                    items: {
                        xtype: 'edituragrid'
                    }
                },
                {
                    xtype: 'panel',
                    padding: '0 2 2 2',
                    title: 'Autori',
                    layout: 'fit',
                    items: {
                        xtype: 'autorgrid'
                    }
                },
                {
                    xtype: 'panel',
                    padding: '0 2 2 2',
                    title: 'Categorii',
                    layout: 'fit',
                    items: {
                        xtype: 'categoriegrid'
                    }
                }
            ]
        }
    ]
});
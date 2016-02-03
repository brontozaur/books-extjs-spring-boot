Ext.define('BM.view.categorie.CategorieGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.categoriegrid',
    selType: 'rowmodel',
    columnLines: true,
    stripeRows: true,
    store: 'CategorieStore',
    dockedItems: [
        {
            xtype: 'pagingtoolbar',
            store: 'CategorieStore',
            dock: 'bottom',
            displayInfo: true,
            border: false
        },
        {
            xtype: 'toolbar',
            border: false,
            items: [
                {
                    iconCls: 'icon-add',
                    text: 'Adaugare',
                    action: 'add-categorie',
                    scope: this
                },
                {
                    iconCls: 'icon-mod',
                    text: 'Modificare',
                    disabled: true,
                    action: 'mod-categorie',
                    scope: this
                },
                {
                    iconCls: 'icon-delete',
                    text: 'Stergere',
                    disabled: true,
                    action: 'del-categorie',
                    scope: this
                },
                {
                    iconCls: 'icon-refresh',
                    text: 'Refresh',
                    action: 'refresh-categorie',
                    scope: this
                }
            ],
            dock: 'top'
        }
    ],

    initComponent: function () {
        var me = this;
        me.columns = this.buildColumns();
        me.callParent(arguments);

        me.store.loadPage(1, {
            params: {
                limit: categoriiPerPage
            },
            callback: function(records, operation, success) {
                if (!success) {
                    me.store.removeAll();
                }
            }
        });
    },

    buildColumns: function () {
        return [
            {
                header: 'Nume categorie',
                dataIndex: 'numeCategorie',
                flex: 1,
                editor: 'textfield'
            }
        ];
    }
});
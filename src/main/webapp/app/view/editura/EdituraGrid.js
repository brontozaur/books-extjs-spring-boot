Ext.define('BM.view.editura.EdituraGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.edituragrid',
    selType: 'rowmodel',
    columnLines: true,
    store: 'EdituraStore',
    stripeRows: true,
    dockedItems: [
        {
            xtype: 'pagingtoolbar',
            store: 'EdituraStore',
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
                    action: 'add-editura',
                    scope: this
                },
                {
                    iconCls: 'icon-mod',
                    text: 'Modificare',
                    disabled: true,
                    action: 'mod-editura',
                    scope: this
                },
                {
                    iconCls: 'icon-delete',
                    text: 'Stergere',
                    disabled: true,
                    action: 'del-editura',
                    scope: this
                },
                {
                    iconCls: 'icon-refresh',
                    text: 'Refresh',
                    action: 'refresh-editura',
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
                start: 0,
                limit: edituriPerPage
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
                header: 'Nume editura',
                dataIndex: 'numeEditura',
                flex: 1,
                editor: 'textfield'
            }
        ];
    }
});
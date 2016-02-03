Ext.define('BM.view.autor.AutorGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.autorgrid',
    selType: 'rowmodel',
    columnLines: true,
    store: 'AutorStore',
    stripeRows: true,
    dockedItems: [
        {
            xtype: 'pagingtoolbar',
            store: 'AutorStore',
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
                    action: 'add-autor',
                    scope: this
                },
                {
                    iconCls: 'icon-mod',
                    text: 'Modificare',
                    disabled: true,
                    action: 'mod-autor',
                    scope: this
                },
                {
                    iconCls: 'icon-delete',
                    text: 'Stergere',
                    disabled: true,
                    action: 'del-autor',
                    scope: this
                },
                {
                    iconCls: 'icon-refresh',
                    text: 'Refresh',
                    action: 'refresh-autor',
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
                limit: autoriPerPage
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
                header: 'Nume',
                dataIndex: 'nume',
                flex: 1,
                editor: 'textfield'
            },
            {
                header: 'Data nasterii',
                dataIndex: 'dataNasterii',
                flex: 1,
                renderer: Ext.util.Format.dateRenderer('m/d/Y')
            }
        ];
    }
});
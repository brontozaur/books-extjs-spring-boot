Ext.define('BM.view.autor.AutorGrid', {
            extend: 'Ext.grid.Panel',
            alias: 'widget.autorgrid',
            selType: 'rowmodel',
            columnLines: true,
            store: 'AutorStore',

            // plugins: [
            // Ext.create('Ext.grid.plugin.RowEditing', {
            // clicksToEdit: 2
            // })
            // ],

            // features: [{
            // ftype: 'grouping',
            // }],

            dockedItems: [
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

            initComponent: function() {
                this.columns = this.buildColumns();
                this.callParent(arguments);
            },

            buildColumns: function() {
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
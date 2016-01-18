Ext.define('BM.view.categorie.CategorieGrid', {
            extend: 'Ext.grid.Panel',
            alias: 'widget.categoriegrid',
            selType: 'rowmodel',
            columnLines: true,
            store: 'CategorieStore',
            dockedItems: [
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

            initComponent: function() {
                this.columns = this.buildColumns();
                this.callParent(arguments);
            },

            buildColumns: function() {
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
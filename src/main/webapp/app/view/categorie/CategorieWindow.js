Ext.define('BM.view.categorie.CategorieWindow', {
            extend: 'Ext.window.Window',
            alias: 'widget.categoriewindow',
            title: 'Detalii Categorie',
            minHeight: 200,
            minWidth: 300,
            items: [
                {
                    xtype: 'form',
                    itemId: 'categorieform',
                    minHeight: 200,
                    minWidth: 300,
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'hidden',
                            name: 'idCategorie',
                            value: ''
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: 'Nume',
                            name: 'numeCategorie'
                        }
                    ]
                }
            ],
            buttons: [
                {
                    text: 'Salvare',
                    itemId: 'saveBtn',
                    iconCls: 'icon-save'
                },
                {
                    text: 'Renuntare',
                    itemId: 'cancelBtn'
                }
            ]
        });
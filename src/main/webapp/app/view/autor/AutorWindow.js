Ext.define('BM.view.autor.AutorWindow', {
            extend: 'Ext.window.Window',
            alias: 'widget.autorwindow',
            title: 'Editare detalii autor',
            minHeight: 200,
            minWidth: 300,
            items: [
                {
                    xtype: 'form',
                    itemId: 'autorform',
                    minHeight: 200,
                    minWidth: 300,
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'hidden',
                            name: 'autorId',
                            value: ''
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: 'Nume',
                            name: 'nume'
                        },
                        {
                            xtype: 'datefield',
                            fieldLabel: 'Data nasterii',
                            name: 'dataNasterii'
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
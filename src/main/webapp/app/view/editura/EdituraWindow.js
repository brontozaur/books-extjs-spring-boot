Ext.define('BM.view.editura.EdituraWindow', {
            extend: 'Ext.window.Window',
            alias: 'widget.editurawindow',
            title: 'Detalii Editura',
            minHeight: 200,
            minWidth: 300,
            items: [
                {
                    xtype: 'form',
                    itemId: 'edituraform',
                    minHeight: 200,
                    minWidth: 300,
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'hidden',
                            name: 'idEditura',
                            value: ''
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: 'Nume',
                            name: 'numeEditura'
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
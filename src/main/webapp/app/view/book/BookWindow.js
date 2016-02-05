Ext.require('BM.view.autor.AutorCombo');
Ext.require('BM.view.editura.EdituraCombo');
Ext.require('BM.view.categorie.CategorieCombo');

Ext.define('BM.view.book.BookWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.bookwindow',
    title: 'Editare detalii carte',
    minWidth: 600,
    layout: 'fit',
    items: [
        {
            xtype: 'form',
            itemId: 'bookform',
            name: 'bookform',
            bodyPadding: 10,
            defaults: {
                labelStyle: 'font-weight:bold;'
            },
            items: [
                {
                    xtype: 'hidden',
                    name: 'bookId',
                    value: ''
                },
                {
                    xtype: 'hidden',
                    name: 'frontCoverData',
                    value: ''
                },
                {
                    xtype: 'hidden',
                    name: 'backCoverData',
                    value: ''
                },
                {
                    xtype: 'container',
                    layout: 'hbox',
                    items: [
                        {
                            xtype: 'container',
                            defaults: {
                                labelStyle: 'font-weight:bold;'
                            },
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: 'Titlu',
                                    name: 'title',
                                    labelWidth: 50,
                                    width: 330
                                },
                                {
                                    xtype: 'container',
                                    layout: 'hbox',
                                    defaults: {
                                        labelStyle: 'font-weight:bold;'
                                    },
                                    items: [
                                        {
                                            xtype: 'autorCombo',
                                            fieldLabel: 'Autor',
                                            name: 'idAutor',
                                            labelWidth: 50,
                                            width: 190,
                                            margin: '0 0 5 0'
                                        },
                                        {
                                            xtype: 'button',
                                            iconCls: 'icon-add',
                                            itemId: 'addAutor'
                                        },
                                        {
                                            xtype: 'checkbox',
                                            fieldLabel: 'Citita',
                                            name: 'citita',
                                            labelWidth: 40,
                                            width: 70,
                                            margin: '0 0 5 5'
                                        }
                                    ]
                                },
                                {
                                    xtype: 'textfield',
                                    fieldLabel: 'Titlu original',
                                    labelWidth: 95,
                                    width: 330,
                                    name: 'originalTitle',
                                    margin: '0 0 5 0'
                                },
                                {
                                    xtype: 'textfield',
                                    fieldLabel: 'Serie',
                                    name: 'serie',
                                    labelWidth: 95,
                                    width: 330
                                },
                                {
                                    xtype: 'container',
                                    layout: 'hbox',
                                    defaults: {
                                        labelStyle: 'font-weight:bold;'
                                    },
                                    items: [
                                        {
                                            xtype: 'numberfield',
                                            fieldLabel: 'Latime (mm)',
                                            name: 'width',
                                            labelWidth: 95,
                                            width: 150
                                        },
                                        {
                                            xtype: 'numberfield',
                                            fieldLabel: 'Nr. pagini',
                                            name: 'nrPagini',
                                            labelWidth: 70,
                                            margin: '0 0 5 5',
                                            width: 125
                                        }
                                    ]
                                },

                                {
                                    xtype: 'numberfield',
                                    fieldLabel: 'Inaltime (mm)',
                                    name: 'height',
                                    labelWidth: 95,
                                    width: 150,
                                    margin: '0 0 5 0'
                                },
                                {
                                    xtype: 'datefield',
                                    fieldLabel: 'An aparitie',
                                    name: 'anAparitie',
                                    width: 190,
                                    labelWidth: 95,
                                    margin: '0 0 5 0',
                                    format: 'Y',
                                    submitFormat: 'Y'
                                },
                                {
                                    xtype: 'container',
                                    layout: 'hbox',
                                    defaults: {
                                        labelStyle: 'font-weight:bold;'
                                    },
                                    items: [
                                        {
                                            fieldLabel: 'Editura',
                                            xtype: 'edituraCombo',
                                            name: 'idEditura',
                                            labelWidth: 50,
                                            width: 170
                                        },
                                        {
                                            xtype: 'button',
                                            iconCls: 'icon-add',
                                            itemId: 'addEditura'
                                        }
                                    ]
                                },
                                {
                                    xtype: 'container',
                                    layout: 'hbox',
                                    defaults: {
                                        labelStyle: 'font-weight:bold;'
                                    },
                                    items: [
                                        {
                                            fieldLabel: 'Gen',
                                            xtype: 'categorieCombo',
                                            name: 'idCategorie',
                                            labelWidth: 50,
                                            width: 170,
                                            margin: '0 0 5 0'
                                        },
                                        {
                                            xtype: 'button',
                                            iconCls: 'icon-add',
                                            itemId: 'addCategorie'
                                        }
                                    ]
                                },
                                {
                                    xtype: 'textfield',
                                    fieldLabel: 'ISBN',
                                    name: 'isbn',
                                    labelWidth: 50
                                },
                                {
                                    xtype: 'container',
                                    layout: 'hbox',
                                    defaults: {
                                        labelStyle: 'font-weight:bold;'
                                    },
                                    items: []
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            layout: 'card',
                            itemId: 'cardLayoutPanel',
                            activeItem: 'frontUploadform',
                            border: false,
                            padding: '0 0 0 40',
                            dockedItems: [
                                {
                                    xtype: 'toolbar',
                                    itemId: 'coversToolbar',
                                    dock: 'top',
                                    style: {
                                        background: 'transparent'
                                    },
                                    items: [
                                        {
                                            xtype: 'button',
                                            itemId: 'frontCoverButton',
                                            text: 'Front cover',
                                            iconCls: 'icon-back'
                                        },
                                        '->',
                                        {
                                            xtype: 'button',
                                            text: 'Back cover',
                                            itemId: 'backCoverButton',
                                            iconCls: 'icon-forward',
                                            iconAlign: 'right'
                                        }
                                    ]
                                }
                            ],
                            items: [
                                {
                                    layout: 'vbox',
                                    xtype: 'form',
                                    border: false,
                                    itemId: 'frontUploadform',
                                    padding: '5 10 0 5',
                                    header: false,
                                    items: [
                                        {
                                            xtype: 'image',
                                            height: 180,
                                            width: 160,
                                            itemId: 'frontCoverPreview',
                                            name: 'frontCover'
                                        },
                                        {
                                            layout: 'vbox',
                                            xtype: 'container',
                                            border: true,
                                            header: false,
                                            align: 'center',
                                            defaults: {
                                                labelStyle: 'font-weight:bold;'
                                            },
                                            items: [
                                                {
                                                    xtype: 'displayfield',
                                                    fieldLabel: 'Front cover:'
                                                },
                                                {
                                                    xtype: 'container',
                                                    layout: 'hbox',
                                                    align: 'left',
                                                    items: [
                                                        {
                                                            xtype: 'filefield',
                                                            buttonOnly: true,
                                                            name: 'frontCoverUpload',
                                                            buttonConfig: {
                                                                iconCls: 'icon-upload'
                                                            },
                                                            listeners:{
                                                                afterrender:function(cmp){
                                                                    cmp.fileInputEl.set({
                                                                        accept:'image/*'
                                                                    });
                                                                }
                                                            }
                                                        },
                                                        {
                                                            xtype: 'tbspacer',
                                                            width: 5
                                                        },
                                                        {
                                                            xtype: 'button',
                                                            iconCls: 'icon-delete',
                                                            itemId: 'removeFrontUpload'
                                                        }
                                                    ]
                                                }
                                            ]
                                        }
                                    ]
                                },
                                {
                                    layout: 'vbox',
                                    xtype: 'form',
                                    border: false,
                                    itemId: 'backUploadform',
                                    padding: '5 10 0 5',
                                    header: false,
                                    items: [
                                        {
                                            xtype: 'image',
                                            height: 180,
                                            width: 160,
                                            itemId: 'backCoverPreview',
                                            name: 'backCover'
                                        },
                                        {
                                            layout: 'vbox',
                                            xtype: 'container',
                                            border: true,
                                            header: false,
                                            align: 'center',
                                            defaults: {
                                                labelStyle: 'font-weight:bold;'
                                            },
                                            items: [
                                                {
                                                    xtype: 'displayfield',
                                                    fieldLabel: 'Back cover:'
                                                },
                                                {
                                                    xtype: 'container',
                                                    layout: 'hbox',
                                                    align: 'left',
                                                    items: [
                                                        {
                                                            xtype: 'filefield',
                                                            buttonOnly: true,
                                                            name: 'backCoverUpload',
                                                            buttonConfig: {
                                                                iconCls: 'icon-upload'
                                                            },
                                                            listeners:{
                                                                afterrender:function(cmp){
                                                                    cmp.fileInputEl.set({
                                                                        accept:'image/*'
                                                                    });
                                                                }
                                                            }
                                                        },
                                                        {
                                                            xtype: 'tbspacer',
                                                            width: 5
                                                        },
                                                        {
                                                            xtype: 'button',
                                                            iconCls: 'icon-delete',
                                                            itemId: 'removeBackUpload'
                                                        }
                                                    ]
                                                }
                                            ]
                                        }
                                    ]
                                }
                            ]
                        }]
                }]
        }],
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
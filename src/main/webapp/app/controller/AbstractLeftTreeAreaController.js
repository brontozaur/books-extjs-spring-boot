Ext.define('BM.controller.AbstractLeftTreeAreaController', {
    extend: 'Ext.app.Controller',

    refs: [
        {
            ref: 'leftTreeArea',
            selector: 'lefttree'
        }
    ],

    init: function () {
        this.control({
            'lefttree tool[itemId=refreshTool]': {
                click: this.refreshTree
            },
            'lefttree tool[itemId=addTool]': {
                click: this.add
            }
        });
    },

    add: function (toolItem, event, eOpts) {
    },

    getActiveItem: function () {
        var me = this;
        return me.getLeftTreeArea().getLayout().getActiveItem();
    },

    refreshTree: function (toolItem, event, eOpts) {
    },

    refreshTreeInternal: function () {
        var me = this;
        var activeTree = me.getActiveItem();
        var store = activeTree.getStore();
        store.getRootNode().removeAll();
        store.load();
    },

    setTitle: function (title) {
        var me = this;
        me.getLeftTreeArea().setTitle(title);
    },

    showMenu: function (panel, event, options) {
        event.stopEvent();
        contextMenu.showAt(event.getXY());
        contextMenu.controller = this;
    },

    itemContextMenu: function (xx, record, item, index, e, eOpts) {
        e.stopEvent();
        contextMenu.controller = this;
        contextMenu.showAt(e.getXY());
    },

    setActiveView: function (treeItemId) {
        var me = this;
        var cardLayout = me.getLeftTreeArea().getLayout();
        var desiredPanel = Ext.ComponentQuery.query('panel[itemId=' + treeItemId + ']')[0];
        cardLayout.setActiveItem(treeItemId);
        me.getLeftTreeArea().setTitle('Grupare dupa ' + desiredPanel.name);
    }
});

var addAction = Ext.create('Ext.Action', {
    iconCls: 'icon-add',
    text: 'Adauga',
    handler: function (widget, event) {
        contextMenu.controller.add();
    }
});

var contextMenu = Ext.create('Ext.menu.Menu', {
    items: [
        addAction,
        {
            text: 'Grupare...',
            menu: {
                items: [
                    {
                        text: 'Autori',
                        checked: true,
                        group: 'vizualizare',
                        handler: function (widget, event) {
                            contextMenu.controller.setActiveView('treeAutori');
                        }
                    },
                    {
                        text: 'Carti',
                        checked: false,
                        group: 'vizualizare',
                        handler: function (widget, event) {
                            contextMenu.controller.setActiveView('treeBooks');
                        }
                    },
                    {
                        text: 'Edituri',
                        checked: false,
                        group: 'vizualizare',
                        handler: function (widget, event) {
                            contextMenu.controller.setActiveView('treeEditura');
                        }
                    }
                ]
            }
        }
    ]
});
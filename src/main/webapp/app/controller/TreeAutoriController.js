Ext.define('BM.controller.TreeAutoriController', {
    extend: 'BM.controller.AbstractLeftTreeAreaController',
    stores: [
        'TreeAutoriStore'
    ],

    views: [
        'tree.TreeAutori'
    ],

    refs: [
        {
            ref: 'tree',
            selector: 'treeautori'
        },
        {
            ref: 'leftTreeArea',
            selector: 'lefttree'
        }
    ],

    init: function () {
        var me = this;
        me.control({
            'treeautori': {
                itemclick: this.itemClick,
                itemcontextmenu: this.itemContextMenu,
                containercontextmenu: this.showMenu
            }
        });
        me.callParent(arguments);
    },

    itemClick: function (tree, recordItem, item, index, e, eOpts) {
        var treeItemValue = recordItem.get('name');
        var isRoot = recordItem.isRoot();
        var grid = Ext.ComponentQuery.query('booksgrid')[0];
        var store = grid.getStore();
        store.clearFilter(true);
        store.filter([
            {
                filterFn: function (record) {
                    if (isRoot || Ext.isEmpty(treeItemValue)) {
                        return true;
                    }
                    var numeAutor = record.get('authorName');
                    if (!numeAutor) {
                        if ("#" === treeItemValue) {
                            return true;
                        }
                        numeAutor = '';
                    }
                    numeAutor = numeAutor.toLowerCase();
                    return numeAutor.indexOf(treeItemValue.toLowerCase()) === 0; // starts with this letter
                }
            }
        ]);
        clearInfoAreaFields();
        enableAutorGridButtons(false);
    },

    add: function () {
        var me = this;
        if (me.getTree() === me.getActiveItem()) {
            var window = Ext.widget('autorwindow');
            window.show();
        }
    },

    refreshTree: function () {
        var me = this;
        if (me.getTree() === me.getActiveItem()) {
            me.refreshTreeInternal();
        }
    }
});
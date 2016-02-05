Ext.define('BM.controller.TreeEdituraController', {
    extend: 'BM.controller.AbstractLeftTreeAreaController',
    stores: [
        'TreeEdituraStore'
    ],

    views: [
        'tree.TreeEditura'
    ],

    refs: [
        {
            ref: 'tree',
            selector: 'treeeditura'
        },
        {
            ref: 'leftTreeArea',
            selector: 'lefttree'
        }
    ],

    init: function () {
        var me = this;
        me.control({
            'treeeditura': {
                itemclick: this.itemClick,
                itemcontextmenu: this.itemContextMenu,
                containercontextmenu: this.showMenu
            }
        });
        me.callParent(arguments);
    },

    itemClick: function (tree, recordItem, item, index, e, eOpts) {
        var treeItemName = recordItem.get('id');
        var isRoot = recordItem.isRoot();
        var grid = Ext.ComponentQuery.query('booksgrid')[0];
        //we need to reload books tree. Because of the pagination, the store filtering is now broken
        //To be able to work with pagination and selection in the left tree, some additional info is required
        var store = grid.getStore();
        store.filterValue = treeItemName;
        store.searchType = isRoot? 'grid' : 'treeEdituri';
        store.loadPage(1, {
            params: {
                limit: booksPerPage,
                filterValue: store.filterValue,
                searchType: store.searchType
            },
            callback: function(records, operation, success) {
                if (!success) {
                    store.removeAll();
                }
            }
        });
    },

    add: function () {
        var me = this;
        if (me.getTree() === me.getActiveItem()) {
            var window = Ext.widget('editurawindow');
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
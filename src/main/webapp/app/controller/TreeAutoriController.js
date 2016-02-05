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
        var treeItemName = recordItem.get('id');
        var isRoot = recordItem.isRoot();
        var grid = Ext.ComponentQuery.query('booksgrid')[0];
        //we need to reload books tree. Because of the pagination, the store filtering is now broken
        //To be able to work with pagination and selection in the left tree, some additional info is required
        var store = grid.getStore();
        store.filterValue = treeItemName;
        store.searchType = isRoot? 'grid' : 'treeAutori';
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
        clearInfoAreaFields();
        enableBookGridButtons(false);
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
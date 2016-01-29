Ext.define('BM.controller.TreeBooksController', {
            extend: 'BM.controller.AbstractLeftTreeAreaController',
            stores: [
                'TreeBooksStore'
            ],

            views: [
                'tree.TreeBooks'
            ],

            refs: [
                {
                    ref: 'tree',
                    selector: 'treebooks'
                },
                {
                    ref: 'leftTreeArea',
                    selector: 'lefttree'
                }
            ],

            init: function() {
                var me = this;
                me.control({
                            'treebooks': {
                                itemclick: this.itemClick,
                                itemcontextmenu: this.itemContextMenu,
                                containercontextmenu: this.showMenu
                            }
                        });
                me.callParent(arguments);
            },

            itemClick: function(tree, recordItem, item, index, e, eOpts) {
                var treeItemValue = recordItem.get('name');
                var isRoot = recordItem.isRoot();
                var grid = Ext.ComponentQuery.query('booksgrid')[0];
                var store = grid.getStore();
                store.clearFilter(true);
                store.filter([
                    {
                        filterFn: function(record) {
                            if (isRoot || Ext.isEmpty(treeItemValue)) {
                                return true;
                            }
                            var numeCarte = record.get('title');
                            if (!numeCarte){
                            	numeCarte = '';
                            }
                            numeCarte = numeCarte.toLowerCase();                            
                            return numeCarte.indexOf(treeItemValue.toLowerCase()) === 0; // starts with this letter
                        }
                    }
                ]);
                clearInfoAreaFields();
                enablebuttons(false);
            },

            add: function() {
                var me = this;
                if (me.getTree() === me.getActiveItem()) {
                    var window = Ext.widget('bookwindow');
                    window.show();
                }
            },

            refreshTree: function() {
                var me = this;
                if (me.getTree() === me.getActiveItem()) {
                    me.refreshTreeInternal();
                }
            }
        });
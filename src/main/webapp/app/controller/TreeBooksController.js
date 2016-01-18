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
                },
                {
                    ref: 'changeViewButton',
                    selector: 'lefttree tool[itemId=toggleTool]'
                }
            ],

            init: function() {
                var me = this;
                me.control({
                            'treebooks': {
                                beforeload: this.loadParamsToRequest,
                                itemclick: this.itemClick,
                                itemcontextmenu: this.itemContextMenu,
                                containercontextmenu: this.showMenu
                            }
                        });
                me.callParent(arguments);
            },

            loadParamsToRequest: function(store, operation, eOpts) {
                var node = operation.node;
                store.proxy.extraParams.nodeId = node.get('name');
                store.proxy.extraParams.root = node.isRoot();
                store.proxy.extraParams.displayMode = this.getTree().displayMode;
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
            },

            setActiveView: function(tree) {
                var me = this;
                if (me.getTree() === me.getActiveItem()) {
                    me.setActiveViewInternal(tree);
                }
            }
        });
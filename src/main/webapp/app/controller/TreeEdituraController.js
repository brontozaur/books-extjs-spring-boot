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
                },
                {
                    ref: 'changeViewButton',
                    selector: 'lefttree tool[itemId=toggleTool]'
                }
            ],

            init: function() {
                var me = this;
                me.control({
                            'treeeditura': {
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
                            var numeEditura = record.get('numeEditura');
                            if (!numeEditura){
                            	numeEditura = '';
                            }
                            numeEditura = numeEditura.toLowerCase();
                            return numeEditura.indexOf(treeItemValue.toLowerCase()) === 0; // starts with this letter
                        }
                    }
                ]);
                clearInfoAreaFields();
                enablebuttonsEditura(false);
            },

            add: function() {
                var me = this;
                if (me.getTree() === me.getActiveItem()) {
                    var window = Ext.widget('editurawindow');
                    window.show();
                }
            },

            refreshTree: function() {
                var me = this;
                if (me.getTree() === me.getActiveItem()) {
                    me.refreshTreeInternal();
                }
            },

            changeView: function(toolItem, event, eOpts) {
                var me = this;
                if (me.getTree() === me.getActiveItem()) {
                    me.changeViewInternal();
                }
            },

            setActiveView: function(tree) {
                var me = this;
                if (me.getTree() === me.getActiveItem()) {
                    me.setActiveViewInternal(tree);
                }
            }
        });
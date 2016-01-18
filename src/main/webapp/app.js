Ext.application({
            requires: [
                'Ext.container.Viewport'
            ],
            name: 'BM',

            appFolder: 'app',

            controllers: [
                'BooksGridController',
                'AbstractLeftTreeAreaController',
                'TreeAutoriController',
                'TreeBooksController',
                'TreeEdituraController',
                'CategorieGridController',
                'EdituraGridController',
                'AutorGridController',
                'BookWindowController',
                'ErrorWindowController'
            ],

            autoCreateViewport: true,

            launch: function() {
                console.log("Powered by ExtJS: " + Ext.getVersion());
                setCurrentTheme('classic');
            }
        });
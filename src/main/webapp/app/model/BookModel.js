Ext.define('BM.model.BookModel', {
    extend: 'Ext.data.Model',
    idProperty: 'bookId',
    proxy: {
        type: 'rest',
        url: '/book',
        reader: {
            type: 'json',
            rootProperty: 'content'
        }
    },
    fields: [
        'bookId',
        'author',
        {
            name: 'title',
            type: 'string'
        },
        'originalTitle',
        'isbn',
        'citita',
        'serie',
        'nrPagini',
        'width',
        'height',
        {
            name: 'idAutor',
            mapping: 'author.autorId'
        },
        {
            name: 'authorName',
            mapping: 'author.nume'
        },
        {
            name: 'dataAparitie',
            type: 'date'
        },
        {
            name: 'idEditura',
            mapping: 'editura.idEditura'
        },
        {
            name: 'numeEditura',
            mapping: 'editura.numeEditura'
        },
        {
            name: 'idCategorie',
            mapping: 'categorie.idCategorie'
        },
        {
            name: 'numeCategorie',
            mapping: 'categorie.numeCategorie'
        },
        {
            name: 'frontCover',
            mapping: 'bookCover.front'
        },
        {
            name: 'backCover',
            mapping: 'bookCover.back'
        }
    ]
});
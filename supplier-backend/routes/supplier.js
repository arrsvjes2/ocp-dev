const express = require('express')
const repository = require('../repository/memory')

const supplierRoute = express.Router()

supplierRoute.get('/', (req, res) => {
    let id = req.query.id
    req.log.info({message: 'request supplier id ' +id})
    let supplierInfo = repository.get(id)
    if (!supplierInfo) {
        return res.status(404).send('not found')
    }
    res.json(supplierInfo)
});


module.exports = supplierRoute
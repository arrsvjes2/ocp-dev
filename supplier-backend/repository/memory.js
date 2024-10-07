const Supplier = require('../model/supplier')

const supplierMap = new Map();

// init supplierMap
let supplier1 = new Supplier()
supplier1.id ='0101'
supplier1.company_name = 'Super portal'
supplier1.contact_name = 'Mr Clean'
supplier1.phone = '(910) 555-5445'
supplierMap.set('0101',  supplier1)

let supplier2 = new Supplier()
supplier2.id ='0102'
supplier2.company_name = 'Pro vision'
supplier2.contact_name = 'Will Sight'
supplier2.phone = '(803) 555-5625'
supplierMap.set('0102',  supplier2)

class Repository {

    get(id) {
        if (supplierMap.has(id)) {
            return supplierMap.get(id)
        }
        return null
    }

    set(supplier) {
        supplierMap.set(supplier.id, supplier)
    }
}

const memoryRepository = new Repository()

module.exports = memoryRepository

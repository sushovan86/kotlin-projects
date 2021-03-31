package com.chak.sc.repo.queries

const val PRODUCT_STATUS = """
SELECT   p.*,
         Count(p2.id) filter ( WHERE p2.status = 'Sold')      sold_count,
         count(p2.id) filter ( WHERE p2.status = 'Available') available_count,
         count(p2.id) filter ( WHERE p2.status = 'Returned')  returned_count
FROM     product p,
         productinventory p2
WHERE    p.id = :productId
AND      p2.productid = p.id
GROUP BY p.id,
         p.productdescription,
         p.company,
         p.price
"""

const val INVENTORY_DETAILS = """
SELECT pr_inv.*,
       prod.id                 AS prod_id,
       prod.company            AS prod_company,
       prod.productdescription AS prod_productdescription,
       prod.price              AS prod_price
FROM   productinventory pr_inv
       LEFT JOIN product prod
              ON prod.id = pr_inv.productid
WHERE  pr_inv.id = :inventoryId    
"""
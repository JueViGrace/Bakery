package com.bakery.core.types.aliases

import com.bakery.core.database.Bakery_order
import com.bakery.core.database.Bakery_order_products
import com.bakery.core.database.FindOrderByUserWithLines
import com.bakery.core.database.FindOrderWithLines

typealias DbOrder = Bakery_order
typealias OrderWithLines = FindOrderWithLines
typealias OrderByUserWithLines = FindOrderByUserWithLines
typealias OrderLines = Bakery_order_products

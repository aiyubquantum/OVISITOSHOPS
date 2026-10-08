package com.example.data.repository

import com.example.R
import com.example.data.model.*

object InitialData {

    val defaultMerchant = Merchant(
        uuid = "9d77266e-a0ac-45de-9320-c12323c6add9",
        name = "Muhammad Aiyub",
        businessName = "Ade Kak Nah",
        verifiedStatus = "verified"
    )

    val defaultStore = Store(
        id = "01a08c17-ee6e-70f7-bb22-96f4fac75ecd",
        name = "Merchandise Store",
        slug = "merchandise-store",
        address = "Aceh, Banda Aceh, Aceh",
        phone = "08778878889"
    )

    val categories: List<CategoryDisplayItem> = listOf(
        CategoryDisplayItem(
            id = "cat_all",
            name = "Semua",
            slug = "semua",
            productCount = 1578,
            iconKey = "all",
            isPrimary = true
        ),
        CategoryDisplayItem(
            id = "ec0b0010-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Oleh-oleh",
            slug = "oleh-oleh",
            productCount = 312,
            iconKey = "gift",
            isPrimary = true
        ),
        CategoryDisplayItem(
            id = "ec0b0020-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Kuliner",
            slug = "kuliner",
            productCount = 198,
            iconKey = "culinary",
            isPrimary = true
        ),
        CategoryDisplayItem(
            id = "eb0a0001-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Fashion",
            slug = "fashion",
            productCount = 254,
            iconKey = "fashion",
            isPrimary = true
        ),
        CategoryDisplayItem(
            id = "ec0b0040-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Kerajinan",
            slug = "kerajinan",
            productCount = 167,
            iconKey = "craft"
        ),
        CategoryDisplayItem(
            id = "ec0b0050-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Kesehatan",
            slug = "kesehatan",
            productCount = 98,
            iconKey = "health"
        ),
        CategoryDisplayItem(
            id = "ec0b0070-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Elektronik",
            slug = "elektronik",
            productCount = 76,
            iconKey = "electronic"
        ),
        CategoryDisplayItem(
            id = "ec0b0080-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Lainnya",
            slug = "lainnya",
            productCount = 65,
            iconKey = "more"
        )
    )

    val categoryCards: List<Category> = listOf(
        Category(
            id = "ec0b0010-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Oleh-oleh Khas Aceh",
            slug = "oleh-oleh-khas-aceh",
            description = "Pilihan lengkap oleh-oleh legendaris dari Tanah Rencong",
            productCount = 312,
            children = listOf(
                Category(id = "sub_kopi", name = "Kopi Gayo & Minuman", slug = "kopi-gayo"),
                Category(id = "sub_kue", name = "Kue & Camilan Tradisional", slug = "kue-camilan")
            )
        ),
        Category(
            id = "ec0b0020-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Kuliner Aceh",
            slug = "kuliner-aceh",
            description = "Cita rasa rempah istimewa dan makanan tradisional Aceh",
            productCount = 198
        ),
        Category(
            id = "eb0a0001-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Fashion & Aksesoris",
            slug = "fashion-aksesoris",
            description = "Pakaian motif Aceh, kopiah meukeutop, dan pakaian souvenir",
            productCount = 254,
            children = listOf(
                Category(id = "eb0a0002-a8eb-11f1-bf0a-78ac4432fe0b", name = "Kaos Oleh-Oleh Khas Aceh", slug = "kaos-oleh-oleh-khas-aceh"),
                Category(id = "eb0a0003-a8eb-11f1-bf0a-78ac4432fe0b", name = "Polo Shirt Custom Aceh", slug = "polo-shirt-custom-aceh"),
                Category(id = "ec0b0021-a8eb-11f1-bf0a-78ac4432fe0b", name = "Kaos & Jaket Komunitas", slug = "kaos-jaket-komunitas")
            )
        ),
        Category(
            id = "ec0b0040-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Kerajinan Tangan",
            slug = "kerajinan-tangan",
            description = "Karya seni ukir, bros motif pinto Aceh, dan anyaman kasab",
            productCount = 167
        ),
        Category(
            id = "ec0b0050-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Kesehatan & Herbal",
            slug = "kesehatan-herbal",
            description = "Minyak nilam kualitas ekspor, sarikurma, dan madu murni",
            productCount = 98
        ),
        Category(
            id = "ec0b0060-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Makanan & Minuman",
            slug = "makanan-minuman",
            description = "Aneka kudapan lezat, teh pucuk gayo, dan dendeng sapi",
            productCount = 466
        ),
        Category(
            id = "ec0b0070-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Elektronik & Gadget",
            slug = "elektronik-gadget",
            description = "Aksesoris gadget dan perlengkapan travel digital",
            productCount = 76
        ),
        Category(
            id = "ec0b0080-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Lainnya",
            slug = "lainnya",
            description = "Perlengkapan khas dan cinderamata Serambi Mekkah",
            productCount = 65
        )
    )

    val products: List<Product> = listOf(
        // Product 1 from publimarket.md §6.1
        Product(
            id = "f49f9e1e-04f9-4a40-9395-29ea0dda0bf3",
            merchantUuid = defaultMerchant.uuid,
            storeId = defaultStore.id,
            categoryId = "eb0a0001-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "KAOS EXCLUSIVE – ULANG TAHUN FAKULTAS TEKNIK USK KE-63",
            slug = "kaos-exclusive-ulang-tahun-fakultas-teknik-usk-ke-63",
            sku = "SKU-RNBTNWFJ",
            description = "Kaos eksklusif edisi terbatas ulang tahun Fakultas Teknik USK ke-63. Terbuat dari katun combed 30s premium bertekstur sejuk, sablon plastisol halus tahan lama, potongan regular fit dengan sentuhan motif teknik Aceh.",
            price = "150000.00",
            discountPrice = "120000.00",
            finalPrice = 120000.0,
            stock = 92,
            weight = "0.10",
            images = listOf("souvenir/products/kaos_ft_usk.png"),
            rating = 4.9,
            reviewCount = 540,
            isBestSeller = true,
            isLocalAceh = true,
            isHalal = true,
            featured = true,
            category = Category(
                id = "ec0b0021-a8eb-11f1-bf0a-78ac4432fe0b",
                name = "Kaos & Jaket Komunitas",
                slug = "kaos-jaket-komunitas"
            ),
            merchant = defaultMerchant,
            store = defaultStore
        ),
        // Product 2 from mockup
        Product(
            id = "prod_kopi_gayo",
            merchantUuid = defaultMerchant.uuid,
            storeId = defaultStore.id,
            categoryId = "ec0b0010-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Kopi Gayo Arabica Premium 250gr",
            slug = "kopi-gayo-arabica-premium-250gr",
            sku = "SKU-KPG-01",
            description = "Kopi Gayo Arabica berasal dari dataran tinggi Gayo, Aceh Tengah. Memiliki cita rasa khas dengan aroma floral, body yang seimbang, dan tingkat keasaman rendah. Cocok untuk pecinta kopi sejati.",
            price = "95000.00",
            discountPrice = "85000.00",
            finalPrice = 85000.0,
            stock = 45,
            weight = "0.25",
            images = listOf("products/kopi_gayo.png"),
            rating = 4.8,
            reviewCount = 1200,
            isBestSeller = true,
            isLocalAceh = true,
            isHalal = true,
            featured = true,
            localDrawableRes = R.drawable.img_kopi_gayo,
            category = Category(
                id = "ec0b0010-a8eb-11f1-bf0a-78ac4432fe0b",
                name = "Oleh-oleh Khas Aceh",
                slug = "oleh-oleh-khas-aceh"
            ),
            merchant = defaultMerchant,
            store = defaultStore
        ),
        // Product 3 from mockup
        Product(
            id = "prod_kue_bhoi",
            merchantUuid = defaultMerchant.uuid,
            storeId = defaultStore.id,
            categoryId = "ec0b0010-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Kue Bhoi Khas Aceh Isi 10 pcs",
            slug = "kue-bhoi-khas-aceh",
            sku = "SKU-KBH-02",
            description = "Kue Bhoi adalah bolu tradisional khas Aceh dengan tekstur luar yang renyah dan bagian dalam empuk lembut, dicetak bentuk ikan dan bunga khas. Isi 10 pcs, tahan lama tanpa bahan pengawet.",
            price = "50000.00",
            discountPrice = "45000.00",
            finalPrice = 45000.0,
            stock = 60,
            weight = "0.30",
            images = listOf("products/kue_bhoi.png"),
            rating = 4.7,
            reviewCount = 866,
            isBestSeller = true,
            isLocalAceh = true,
            isHalal = true,
            featured = true,
            localDrawableRes = R.drawable.img_kue_bhoi,
            category = Category(
                id = "ec0b0010-a8eb-11f1-bf0a-78ac4432fe0b",
                name = "Oleh-oleh Khas Aceh",
                slug = "oleh-oleh-khas-aceh"
            ),
            merchant = defaultMerchant,
            store = defaultStore
        ),
        // Product 4 from mockup
        Product(
            id = "prod_sarikurma",
            merchantUuid = defaultMerchant.uuid,
            storeId = defaultStore.id,
            categoryId = "ec0b0050-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Sarikurma Aceh 500ml",
            slug = "sarikurma-aceh-500ml",
            sku = "SKU-SRK-03",
            description = "Ekstrak sari kurma pilihan berpadu dengan madu murni hutan Aceh. Memberikan nutrisi alami, energi instan, serta meningkatkan imunitas tubuh secara optimal untuk seluruh keluarga.",
            price = "85000.00",
            discountPrice = "75000.00",
            finalPrice = 75000.0,
            stock = 30,
            weight = "0.50",
            images = listOf("products/sarikurma.png"),
            rating = 4.6,
            reviewCount = 642,
            isBestSeller = false,
            isLocalAceh = true,
            isHalal = true,
            featured = true,
            merchant = defaultMerchant,
            store = defaultStore
        ),
        // Product 5 from mockup
        Product(
            id = "prod_teh_boh_gayo",
            merchantUuid = defaultMerchant.uuid,
            storeId = defaultStore.id,
            categoryId = "ec0b0020-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Teh Boh Gayo 100gr",
            slug = "teh-boh-gayo-100gr",
            sku = "SKU-TBG-04",
            description = "Teh herbal pucuk daun istimewa dari dataran tinggi Gayo, Aceh. Memberikan seduhan aroma segar, rileks, dan menenangkan dengan kandungan antioksidan tinggi.",
            price = "45000.00",
            discountPrice = "40000.00",
            finalPrice = 40000.0,
            stock = 40,
            weight = "0.10",
            images = listOf("products/teh_boh_gayo.png"),
            rating = 4.5,
            reviewCount = 421,
            isBestSeller = false,
            isLocalAceh = true,
            isHalal = true,
            featured = false,
            merchant = defaultMerchant,
            store = defaultStore
        ),
        // Product 6 from mockup
        Product(
            id = "prod_pisang_sale",
            merchantUuid = defaultMerchant.uuid,
            storeId = defaultStore.id,
            categoryId = "ec0b0010-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Pisang Sale Aceh 250gr",
            slug = "pisang-sale-aceh-250gr",
            sku = "SKU-PSA-05",
            description = "Pisang sale khas Aceh dengan proses pengasapan tradisional dan balutan karamel gula tebu alami. Rasa manis legit khas dan gurih renyah di setiap gigitan.",
            price = "40000.00",
            discountPrice = "35000.00",
            finalPrice = 35000.0,
            stock = 55,
            weight = "0.25",
            images = listOf("products/pisang_sale.png"),
            rating = 4.8,
            reviewCount = 389,
            isBestSeller = true,
            isLocalAceh = true,
            isHalal = true,
            featured = true,
            localDrawableRes = R.drawable.img_pisang_sale,
            merchant = defaultMerchant,
            store = defaultStore
        ),
        // Product 7: Kopiah Meukeutop
        Product(
            id = "prod_kopiah_meukeutop",
            merchantUuid = defaultMerchant.uuid,
            storeId = defaultStore.id,
            categoryId = "eb0a0001-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Kopiah Meukeutop Aceh",
            slug = "kopiah-meukeutop-aceh",
            sku = "SKU-KMK-06",
            description = "Kopiah tradisional pahlawan Teuku Umar dengan filosofi adat Aceh warna merah, kuning, hijau, dan hitam. Dibuat dengan rajutan tangan rapi dan bahan kokoh berkualitas premium.",
            price = "140000.00",
            discountPrice = "125000.00",
            finalPrice = 125000.0,
            stock = 25,
            weight = "0.20",
            images = listOf("products/kopiah_meukeutop.png"),
            rating = 4.9,
            reviewCount = 512,
            isBestSeller = true,
            isLocalAceh = true,
            isHalal = true,
            featured = true,
            merchant = defaultMerchant,
            store = defaultStore
        ),
        // Product 8: Bros Pinto Aceh
        Product(
            id = "prod_bros_pinto_aceh",
            merchantUuid = defaultMerchant.uuid,
            storeId = defaultStore.id,
            categoryId = "ec0b0040-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Bros Motif Pinto Aceh Emas Kuningan",
            slug = "bros-motif-pinto-aceh",
            sku = "SKU-BPA-07",
            description = "Aksesoris elegan bermotif Pinto Aceh khas Kesultanan Iskandar Muda. Terbuat dari kuningan lapis sepuh emas, awet dan mewah dipakai pada jilbab atau pakaian pesta.",
            price = "110000.00",
            discountPrice = "95000.00",
            finalPrice = 95000.0,
            stock = 35,
            weight = "0.08",
            images = listOf("products/bros_pinto.png"),
            rating = 4.8,
            reviewCount = 340,
            isBestSeller = false,
            isLocalAceh = true,
            isHalal = true,
            featured = false,
            merchant = defaultMerchant,
            store = defaultStore
        ),
        // Product 9: Dendeng Aceh
        Product(
            id = "prod_dendeng_aceh",
            merchantUuid = defaultMerchant.uuid,
            storeId = defaultStore.id,
            categoryId = "ec0b0020-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Dendeng Sapi Aceh Asli 250gr",
            slug = "dendeng-sapi-aceh-asli-250gr",
            sku = "SKU-DND-08",
            description = "Dendeng daging sapi segar khas Banda Aceh dipadu rempah ketumbar, asam sunti, dan bumbu warisan turun temurun. Renyah gurih dan tahan berbulan-bulan.",
            price = "125000.00",
            discountPrice = "115000.00",
            finalPrice = 115000.0,
            stock = 20,
            weight = "0.25",
            images = listOf("products/dendeng.png"),
            rating = 4.9,
            reviewCount = 780,
            isBestSeller = true,
            isLocalAceh = true,
            isHalal = true,
            featured = true,
            merchant = defaultMerchant,
            store = defaultStore
        ),
        // Product 10: Minyak Nilam
        Product(
            id = "prod_minyak_nilam",
            merchantUuid = defaultMerchant.uuid,
            storeId = defaultStore.id,
            categoryId = "ec0b0050-a8eb-11f1-bf0a-78ac4432fe0b",
            name = "Minyak Nilam Murni Aceh 30ml",
            slug = "minyak-nilam-murni-aceh-30ml",
            sku = "SKU-NLM-09",
            description = "Minyak atsiri Patchouli kualitas nomor satu dunia dari bumi Serambi Mekkah. Memiliki aroma earthy yang awet dan menenangkan pikiran, cocok untuk relaksasi dan diffuser.",
            price = "175000.00",
            discountPrice = "160000.00",
            finalPrice = 160000.0,
            stock = 18,
            weight = "0.10",
            images = listOf("products/nilam.png"),
            rating = 4.9,
            reviewCount = 295,
            isBestSeller = false,
            isLocalAceh = true,
            isHalal = true,
            featured = false,
            merchant = defaultMerchant,
            store = defaultStore
        )
    )

    val shippingMethods: List<ShippingMethod> = listOf(
        ShippingMethod(
            id = "ship_jne",
            name = "JNE Reguler",
            courierCode = "jne",
            description = "2-3 hari",
            baseCost = "25000.00"
        ),
        ShippingMethod(
            id = "ship_jnt",
            name = "J&T Express",
            courierCode = "jnt",
            description = "1-2 hari",
            baseCost = "35000.00"
        ),
        ShippingMethod(
            id = "ship_sicepat",
            name = "SiCepat",
            courierCode = "sicepat",
            description = "2-3 hari",
            baseCost = "22000.00"
        )
    )

    // Orders from publimarket.md §7.1: format SO-YYYYMMDD-XXXXXXXX
    val sampleOrders: List<Order> = listOf(
        Order(
            id = "ord_001",
            orderNumber = "SO-20261008-HYNTEBQ5",
            status = "shipped",
            statusLabel = "Dikirim",
            paymentStatus = "paid",
            paymentStatusLabel = "Lunas",
            totalAmount = "205000.00",
            formattedTotal = "Rp 205.000",
            shippingCost = "25000.00",
            courier = "jne",
            trackingNumber = "JNE1234567890",
            orderedAt = "2026-10-08 10:30",
            estimatedDelivery = "10 Okt 2026 - 12 Okt 2026",
            shippingAddress = ShippingAddress(
                name = "Muhammad Aiyub",
                phone = "6281360313113",
                address = "Jl. Teuku Umar No. 12",
                city = "Banda Aceh",
                postalCode = "23116"
            ),
            items = listOf(
                OrderItem(
                    id = "item_1",
                    productUuid = "f49f9e1e-04f9-4a40-9395-29ea0dda0bf3",
                    productName = "KAOS EXCLUSIVE – ULANG TAHUN FAKULTAS TEKNIK USK KE-63",
                    price = "120000.00",
                    formattedPrice = "Rp 120.000",
                    quantity = 1,
                    subtotal = "120000.00",
                    formattedSubtotal = "Rp 120.000",
                    product = products[0]
                ),
                OrderItem(
                    id = "item_2",
                    productUuid = "prod_kopi_gayo",
                    productName = "Kopi Gayo Arabica Premium 250gr",
                    price = "85000.00",
                    formattedPrice = "Rp 85.000",
                    quantity = 1,
                    subtotal = "85000.00",
                    formattedSubtotal = "Rp 85.000",
                    product = products[1]
                )
            ),
            history = listOf(
                OrderTracking(
                    id = "trk_1",
                    status = "shipped",
                    description = "Paket telah dikirim melalui jne dengan nomor resi JNE1234567890",
                    location = "Banda Aceh",
                    trackedAt = "2026-10-08 11:00"
                ),
                OrderTracking(
                    id = "trk_2",
                    status = "processing",
                    description = "Paket telah disiapkan oleh merchant Ade Kak Nah",
                    location = "Gudang Ovisito Banda Aceh",
                    trackedAt = "2026-10-08 09:30"
                )
            )
        ),
        Order(
            id = "ord_002",
            orderNumber = "SO-20261008-XIMAMZL3",
            status = "pending",
            statusLabel = "Menunggu Pembayaran",
            paymentStatus = "unpaid",
            paymentStatusLabel = "Menunggu Pembayaran",
            totalAmount = "135000.00",
            formattedTotal = "Rp 135.000",
            shippingCost = "15000.00",
            courier = "jne",
            trackingNumber = null,
            orderedAt = "2026-10-08 11:53",
            estimatedDelivery = "2-3 hari",
            paymentUrl = "https://flip.id/pwf-sandbox/\$muhammadaiyub/#pembayaran_SO-20261008-XIMAMZL3",
            flipBillId = "362438",
            shippingAddress = ShippingAddress(
                name = "Muhammad Aiyub",
                phone = "6281360313113",
                address = "Jl. Teuku Umar No. 12",
                city = "Banda Aceh",
                postalCode = "23116"
            ),
            items = listOf(
                OrderItem(
                    id = "item_3",
                    productUuid = "f49f9e1e-04f9-4a40-9395-29ea0dda0bf3",
                    productName = "KAOS EXCLUSIVE – ULANG TAHUN FAKULTAS TEKNIK USK KE-63",
                    price = "120000.00",
                    formattedPrice = "Rp 120.000",
                    quantity = 1,
                    subtotal = "120000.00",
                    formattedSubtotal = "Rp 120.000",
                    product = products[0]
                )
            )
        )
    )
}

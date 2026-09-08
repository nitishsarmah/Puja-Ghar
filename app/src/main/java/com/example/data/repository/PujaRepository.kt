package com.example.data.repository

import com.example.data.local.BookingDao
import com.example.data.local.BookingEntity
import com.example.data.local.CartDao
import com.example.data.local.CartItemEntity
import com.example.data.local.OrderDao
import com.example.data.local.OrderEntity
import com.example.data.model.GuwahatiLocalities
import com.example.data.model.GuwahatiLocality
import com.example.data.model.OfferBanner
import com.example.data.model.PujaService
import com.example.data.model.Pujari
import com.example.data.model.SamagriItem
import com.example.data.model.Store
import com.example.data.model.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class PujaRepository(
    private val bookingDao: BookingDao,
    private val orderDao: OrderDao,
    private val cartDao: CartDao
) {
    // Current Active Guwahati Locality
    private val _selectedLocality = MutableStateFlow(GuwahatiLocalities[0])
    val selectedLocality: StateFlow<GuwahatiLocality> = _selectedLocality.asStateFlow()

    // Active User Role (Customer, Pujari Partner, Store Merchant, Admin)
    private val _currentRole = MutableStateFlow(UserRole.CUSTOMER)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    fun setSelectedLocality(locality: GuwahatiLocality) {
        _selectedLocality.value = locality
    }

    fun setRole(role: UserRole) {
        _currentRole.value = role
    }

    // Static Guwahati Catalog
    val popularPujas: List<PujaService> = listOf(
        PujaService(
            id = "puja_ganesh",
            title = "Shri Ganesh Puja & Vighnaharta Vidhi",
            assameseTitle = "শ্ৰী গণেশ পূজা",
            category = "Popular",
            price = 1499,
            originalPrice = 1899,
            durationMinutes = 60,
            description = "Sacred invocation of Lord Ganesha to remove obstacles, bring good auspicious fortune, and grant success in all endeavors.",
            samagriIncluded = false,
            pujariIncluded = true,
            includedItems = listOf("Ganesh Kalash Sthapana", "108 Durva Arpan", "Modak & Ladoo Bhog", "Sankat Nashan Stotra Path", "Ganesh Maha Aarti"),
            benefits = listOf("Removes all pending obstacles", "Brings positive energy to home", "Auspicious start for work"),
            deity = "Lord Ganesha",
            rating = 4.95f,
            reviewCount = 210,
            isPopular = true,
            isPackage = false,
            samagriOnlyPrice = 799,
            pujariOnlyPrice = 1200,
            packagePrice = 1899
        ),
        PujaService(
            id = "puja_lakshmi",
            title = "Maha Lakshmi Puja & Wealth Blessing",
            assameseTitle = "মহা লক্ষ্মী পূজা",
            category = "Prosperity",
            price = 1799,
            originalPrice = 2200,
            durationMinutes = 75,
            description = "Devotional invocation of Goddess Lakshmi for wealth, domestic harmony, and business prosperity according to Vedic traditions.",
            samagriIncluded = false,
            pujariIncluded = true,
            includedItems = listOf("Ashta Lakshmi Kalash", "Shree Suktam & Kanakadhara Path", "Kamal Gatta & Lotus Arpan", "Kuber Mantra Japa", "Deep Daan & Maha Aarti"),
            benefits = listOf("Financial stability and abundance", "Removes negativity from house", "Blessings of auspicious wealth"),
            deity = "Goddess Lakshmi",
            rating = 4.93f,
            reviewCount = 248,
            isPopular = true,
            isPackage = false,
            samagriOnlyPrice = 899,
            pujariOnlyPrice = 1400,
            packagePrice = 2199
        ),
        PujaService(
            id = "puja_satyanarayan",
            title = "Shri Satyanarayan Puja & Katha",
            assameseTitle = "শ্ৰী সত্যনাৰায়ণ পূজা",
            category = "Popular",
            price = 2100,
            originalPrice = 2500,
            durationMinutes = 120,
            description = "Traditional Satyanarayan Katha for peace, family harmony and divine blessings. Performed with full Vedic rituals and Assamese traditions.",
            samagriIncluded = false,
            pujariIncluded = true,
            includedItems = listOf("Vedic Sankalp", "Ganesh Puja", "Navagraha Smaran", "5 Chapters Katha", "Aarti & Panchamrit Prasad"),
            benefits = listOf("Family peace and prosperity", "Overcomes obstacles", "Auspicious start for new ventures"),
            deity = "Lord Vishnu",
            rating = 4.96f,
            reviewCount = 342,
            isPopular = true,
            isPackage = false,
            samagriOnlyPrice = 999,
            pujariOnlyPrice = 1500,
            packagePrice = 2399
        ),
        PujaService(
            id = "puja_shiv",
            title = "Shiv Puja & Rudrabhishek",
            assameseTitle = "শিৱ পূজা আৰু ৰুদ্ৰাভিষেক",
            category = "Vedic",
            price = 2400,
            originalPrice = 2800,
            durationMinutes = 90,
            description = "Powerful Vedic chanting of Sri Rudram with Panchamrit abhishek of the Shiva Lingam for health, peace and spiritual upliftment.",
            samagriIncluded = false,
            pujariIncluded = true,
            includedItems = listOf("Panchamrit Snan", "108 Sacred Bilva Patra Archana", "Sri Rudram Path", "Maha Mrityunjaya Japa", "Shiva Aarti"),
            benefits = listOf("Relief from chronic ailments", "Inner calm and mental strength", "Destroys negative karma"),
            deity = "Lord Shiva",
            rating = 4.94f,
            reviewCount = 195,
            isPopular = true,
            isPackage = false,
            samagriOnlyPrice = 1099,
            pujariOnlyPrice = 1600,
            packagePrice = 2599
        ),
        PujaService(
            id = "puja_durga",
            title = "Durga Puja & Maa Kamakhya Chandi Path",
            assameseTitle = "দুৰ্গা পূজা আৰু চণ্ডী পাঠ",
            category = "Devi Puja",
            price = 3100,
            originalPrice = 3600,
            durationMinutes = 150,
            description = "Sacred Shakta ritual according to Assam's revered Nilachal Kamakhya traditions with Durga Saptashati Chandi path and kumkum archana.",
            samagriIncluded = false,
            pujariIncluded = true,
            includedItems = listOf("Devi Sthapana", "Durga Saptashati Chandi Recitation", "Sindoor & Rakt Chandan Arpan", "Kanya Pujan (symbolic)", "Maha Devi Aarti"),
            benefits = listOf("Divine protection from adversaries", "Fulfillment of heartfelt desires", "Courage and feminine strength"),
            deity = "Maa Durga / Maa Kamakhya",
            rating = 4.98f,
            reviewCount = 420,
            isPopular = true,
            isPackage = false,
            samagriOnlyPrice = 1299,
            pujariOnlyPrice = 2100,
            packagePrice = 3299
        ),
        PujaService(
            id = "puja_saraswati",
            title = "Maa Saraswati Puja & Vidya Archana",
            assameseTitle = "মা সৰস্বতী পূজা",
            category = "Education & Arts",
            price = 1599,
            originalPrice = 1999,
            durationMinutes = 60,
            description = "Auspicious blessings for students, artists, and professionals to attain wisdom, academic excellence, and mastery of creative arts.",
            samagriIncluded = false,
            pujariIncluded = true,
            includedItems = listOf("Pustak & Pen Puja", "Saraswati Vandana & Stotra", "White flower & Chandan offerings", "Vidya Gayatri Mantra Japa", "Aarti & Prasad"),
            benefits = listOf("Focus and sharp intellect for studies", "Blessings for competitive examinations", "Clarity in artistic pursuits"),
            deity = "Maa Saraswati",
            rating = 4.91f,
            reviewCount = 178,
            isPopular = false,
            isPackage = false,
            samagriOnlyPrice = 750,
            pujariOnlyPrice = 1100,
            packagePrice = 1750
        ),
        PujaService(
            id = "puja_griha_pravesh",
            title = "Griha Pravesh & Vastu Shanti",
            assameseTitle = "গৃহ প্ৰৱেশ আৰু বাস্তু পূজা",
            category = "Auspicious",
            price = 3500,
            originalPrice = 4200,
            durationMinutes = 180,
            description = "Complete auspicious housewarming puja removing negative energies, purifying the dwelling and inviting Goddess Lakshmi and Vastu Devata.",
            samagriIncluded = false,
            pujariIncluded = true,
            includedItems = listOf("Dwar Puja", "Go-Puja (Cow blessing)", "Vastu Homa & Havan Kund", "Navagraha Havan", "Mangal Kalash Sthapana"),
            benefits = listOf("Protects home from negative energies", "Brings abundance and good health", "Purifies living spaces completely"),
            deity = "Vastu Purusha & Goddess Lakshmi",
            rating = 4.97f,
            reviewCount = 289,
            isPopular = true,
            isPackage = false,
            samagriOnlyPrice = 1499,
            pujariOnlyPrice = 2200,
            packagePrice = 3599
        ),
        PujaService(
            id = "puja_vehicle",
            title = "Vehicle Puja (Vahan Puja)",
            assameseTitle = "বাহন পূজা",
            category = "Protection",
            price = 999,
            originalPrice = 1299,
            durationMinutes = 40,
            description = "Auspicious blessings and protective Vedic mantras for new or existing cars, bikes and commercial transport in Guwahati.",
            samagriIncluded = false,
            pujariIncluded = true,
            includedItems = listOf("Ganesh Smaran", "Swastik drawing with Sindoor", "Nimbu-Mirchi & Coconut braking", "Kalawa tying on steering/handles", "Suraksha Mantra Path"),
            benefits = listOf("Protection against travel mishaps", "Peace of mind while driving", "Divine blessings for vehicle longevity"),
            deity = "Lord Ganesha & Lord Hanuman",
            rating = 4.89f,
            reviewCount = 152,
            isPopular = false,
            isPackage = false,
            samagriOnlyPrice = 499,
            pujariOnlyPrice = 799,
            packagePrice = 1199
        ),
        PujaService(
            id = "puja_shop_office",
            title = "Shop & Office Inauguration Puja",
            assameseTitle = "দোকান / কাৰ্যালয় উদ্বোধনী পূজা",
            category = "Business",
            price = 2299,
            originalPrice = 2799,
            durationMinutes = 90,
            description = "Vedic ribbon-opening puja for new businesses, showrooms, clinics, and corporate offices across Guwahati.",
            samagriIncluded = false,
            pujariIncluded = true,
            includedItems = listOf("Kalash Sthapana at entrance", "Ganesh & Lakshmi Abhishekam", "Bahi-Khata / Invoice blessing", "Navagraha Shanti Havan", "Mangal Toran & Aarti"),
            benefits = listOf("Customer footfall and commercial success", "Protection from competitors' evil eye", "Positive workplace atmosphere"),
            deity = "Lord Ganesha & Kuber",
            rating = 4.92f,
            reviewCount = 205,
            isPopular = true,
            isPackage = false,
            samagriOnlyPrice = 1099,
            pujariOnlyPrice = 1600,
            packagePrice = 2599
        ),
        PujaService(
            id = "puja_birthday_family",
            title = "Birthday & Family Ayushya Homa",
            assameseTitle = "জন্মদিন / পৰিয়াল আয়ুষ্য হোম",
            category = "Family & Health",
            price = 1899,
            originalPrice = 2399,
            durationMinutes = 75,
            description = "Divine Vedic birthday ceremony invoking Markandeya, Chiranjeevi, and Ayur Devata for long healthy life and auspicious protection.",
            samagriIncluded = false,
            pujariIncluded = true,
            includedItems = listOf("Sankalp in devotee's Gotra", "Ayushya Suktam recitation", "Maha Mrityunjaya Homa", "Chiranjeevi Smaran", "Raksha Sutra & Ashirwad"),
            benefits = listOf("Good health and longevity", "Protects from premature ailments", "Special family bonding blessing"),
            deity = "Ayur Devata & Lord Shiva",
            rating = 4.90f,
            reviewCount = 135,
            isPopular = false,
            isPackage = false,
            samagriOnlyPrice = 850,
            pujariOnlyPrice = 1350,
            packagePrice = 2099
        )
    )

    val completePackages: List<PujaService> = listOf(
        PujaService(
            id = "pkg_griha_pravesh",
            title = "Complete Griha Pravesh Maha Package",
            assameseTitle = "সম্পূৰ্ণ গৃহ প্ৰৱেশ পেকেজ",
            category = "Complete Package",
            price = 5499,
            originalPrice = 6499,
            durationMinutes = 180,
            description = "Everything handled for your housewarming: 100% Shuddh 45+ Samagri items, brass hawan kund, verified senior Assamese Purohit, and door delivery.",
            samagriIncluded = true,
            pujariIncluded = true,
            includedItems = listOf(
                "Verified Senior Purohit for 3+ hours",
                "Full 48-item Samagri kit delivered 2 hrs prior",
                "Pure Desi Cow Ghee (500g) & Sacred Mango Wood",
                "Assamese Gamusa & Muga vastra set",
                "Kalash with Coconut, Betel leaves, Panchamrit kit",
                "Hawan Kund, camphor, dhoop & fresh flower garland",
                "Post-puja disposal guide for holy ash and flowers"
            ),
            benefits = listOf("Stress-free complete arrangement", "Zero missing items guarantee", "Authentic Vedic vidhi by top pandit"),
            deity = "Vastu Purusha, Lakshmi, Ganesha",
            rating = 4.96f,
            reviewCount = 312,
            isPopular = true,
            isPackage = true
        ),
        PujaService(
            id = "pkg_satyanarayan",
            title = "Satyanarayan Complete Family Package",
            assameseTitle = "সত্যনাৰায়ণ সম্পূৰ্ণ পেকেজ",
            category = "Complete Package",
            price = 3199,
            originalPrice = 3799,
            durationMinutes = 120,
            description = "All-in-one divine setup for Satyanarayan Katha. Includes experienced purohit, all required grains, fruits, panchamrit ingredients and brass lamps.",
            samagriIncluded = true,
            pujariIncluded = true,
            includedItems = listOf(
                "Verified Vedic Purohit",
                "Complete 32-item Samagri box delivered home",
                "Pure Cow Ghee, Honey, Ganga Jal, Gangamrit kit",
                "Tulsi patra, Supari, Sindoor, Haldi, Akshat",
                "Atta & Suji Prasad mix with dry fruits",
                "Prasad bowls and aarti thali preparation"
            ),
            benefits = listOf("No hassle of visiting crowded markets", "Highest quality pure ingredients", "Punctual pandit arrival"),
            deity = "Bhagwan Satyanarayan",
            rating = 4.92f,
            reviewCount = 275,
            isPopular = true,
            isPackage = true
        ),
        PujaService(
            id = "pkg_rudrabhishek",
            title = "Mahadev Rudrabhishek Maha Package",
            assameseTitle = "ৰুদ্ৰাভিষেক সম্পূৰ্ণ পেকেজ",
            category = "Complete Package",
            price = 3499,
            originalPrice = 4200,
            durationMinutes = 100,
            description = "Exhaustive Shiva Abhishekam kit with 108 Bilva patra, Ganga & Brahmaputra jal, Panchamrit, Bhasma and learned Shiva Purohit.",
            samagriIncluded = true,
            pujariIncluded = true,
            includedItems = listOf(
                "Experienced Rudram chanting Shastri",
                "Fresh 108 Sacred Bilva leaves & Dhatura",
                "Brahmaputra & Ganga Pavitra Jal (1 Liter)",
                "Pure Chandan paste, Bhasma & Sugandhi Attar",
                "Desi Ghee brass diya with 5-wick Aarti",
                "Milk, Curd, Honey & Shakkar Panchamrit components"
            ),
            benefits = listOf("Rare authentic items procured fresh", "Melodious Sanskrit recitation", "Sacred atmosphere at home"),
            deity = "Lord Shiva",
            rating = 4.90f,
            reviewCount = 168,
            isPopular = false,
            isPackage = true
        ),
        PujaService(
            id = "pkg_kamakhya_blessing",
            title = "Maa Kamakhya Devi Blessings Package",
            assameseTitle = "মা কামাখ্যা আশীৰ্বাদ পেকেজ",
            category = "Complete Package",
            price = 4199,
            originalPrice = 4999,
            durationMinutes = 150,
            description = "Nilachal hill consecrated samagri with Rakt Chandan, authentic Kamakhya sindoor, silk vastra and revered Devi Purohit.",
            samagriIncluded = true,
            pujariIncluded = true,
            includedItems = listOf(
                "Nilachal Certified Kamakhya Pandit",
                "Authentic Kamakhya temple vermillion & Rakt Chandan",
                "Red silk Devi vastra & silver coin blessing",
                "Hawan samagri with Lotus seeds & Guggal",
                "Fresh red hibiscus garlands & special offerings"
            ),
            benefits = listOf("Authentic temple sacred items", "Direct Nilachal purohit expertise", "Protection and high positive energy"),
            deity = "Maa Kamakhya",
            rating = 4.99f,
            reviewCount = 380,
            isPopular = true,
            isPackage = true
        )
    )

    val featuredPujaris: List<Pujari> = listOf(
        Pujari(
            id = "pujari_bhaskar",
            name = "Pandit Bhaskar Sarma",
            title = "Vedic Purohit & Kamakhya Vidhi Acharya",
            experienceYears = 21,
            languages = listOf("Assamese", "Sanskrit", "Hindi"),
            rating = 4.96f,
            reviewCount = 310,
            locality = "Kamakhya / Nilachal",
            isVerified = true,
            dakshina = 1800,
            specialties = listOf("Kamakhya Chandi Path", "Griha Pravesh", "Satyanarayan"),
            bio = "Born in a traditional priestly lineage of Nilachal hills. Expert in Vedic rituals, Sanskrit pronunciation, and Assamese family customs."
        ),
        Pujari(
            id = "pujari_devajit",
            name = "Acharya Devajit Goswami",
            title = "Shastri & Jyotish Visharad",
            experienceYears = 17,
            languages = listOf("Assamese", "Bengali", "Sanskrit", "Hindi"),
            rating = 4.92f,
            reviewCount = 245,
            locality = "Beltola, Guwahati",
            isVerified = true,
            dakshina = 1500,
            specialties = listOf("Griha Pravesh", "Vastu Shanti", "Vivah Vidhi"),
            bio = "Completed formal Vedic education at Guwahati Sanskrit College. Highly revered across Beltola, Six Mile, and Dispur for punctual and soulful pujas."
        ),
        Pujari(
            id = "pujari_prabin",
            name = "Pandit Prabin Bhattacharya",
            title = "Senior Purohit & Shiva Tantra Scholar",
            experienceYears = 24,
            languages = listOf("Assamese", "Sanskrit", "Hindi"),
            rating = 4.89f,
            reviewCount = 189,
            locality = "Ganeshguri, Guwahati",
            isVerified = true,
            dakshina = 1600,
            specialties = listOf("Rudrabhishek", "Navagraha Shanti", "Mahamrityunjaya"),
            bio = "Specializes in deep Vedic chantings, Rudrabhishek and planetary dosha remedies. Known for patience in explaining ritual significance to family members."
        ),
        Pujari(
            id = "pujari_ratul",
            name = "Acharya Ratul Shastri",
            title = "Vedic Acharya & Hawan Specialist",
            experienceYears = 14,
            languages = listOf("Assamese", "Hindi", "Sanskrit"),
            rating = 4.88f,
            reviewCount = 142,
            locality = "Jalukbari / Maligaon",
            isVerified = true,
            dakshina = 1400,
            specialties = listOf("Ganesh Puja", "Lakshmi Puja", "Hawan Vidhi"),
            bio = "Serving West Guwahati and airport corridor. Known for meticulous Hawan preparation, purity of mantras and warm interaction with children and elders."
        ),
        Pujari(
            id = "pujari_manas",
            name = "Pandit Manas Barman",
            title = "Purohit & Naam-Kirtan Coordinator",
            experienceYears = 16,
            languages = listOf("Assamese", "Bengali", "Sanskrit"),
            rating = 4.85f,
            reviewCount = 112,
            locality = "Uzan Bazar, Guwahati",
            isVerified = true,
            dakshina = 1300,
            specialties = listOf("Naam Kirtan", "Bhagavat Path", "Satyanarayan"),
            bio = "Deep connection with Assamese Vaishnavite kirtan and smarta puja traditions along Brahmaputra riverfront."
        )
    )

    val nearbyStores: List<Store> = listOf(
        Store(
            id = "store_kamakhya",
            name = "Kamakhya Pavitra Samagri Bhandar",
            locality = "Nilachal Hill / Kamakhya",
            address = "Shop 14, Kamakhya Temple Road, Guwahati",
            rating = 4.9f,
            distanceKm = 1.4,
            deliveryTimeMin = 30,
            isVerified = true,
            phone = "+91 98641 55221"
        ),
        Store(
            id = "store_ganeshguri",
            name = "Assam Vedic Store & Bhandar",
            locality = "Ganeshguri",
            address = "Near Ganesh Mandir, GS Road, Guwahati",
            rating = 4.8f,
            distanceKm = 2.1,
            deliveryTimeMin = 25,
            isVerified = true,
            phone = "+91 98642 66332"
        ),
        Store(
            id = "store_uzanbazar",
            name = "Brahmaputra Pavitra Store",
            locality = "Uzan Bazar",
            address = "Latasil Point, Uzan Bazar Ghat Road, Guwahati",
            rating = 4.85f,
            distanceKm = 1.8,
            deliveryTimeMin = 20,
            isVerified = true,
            phone = "+91 98643 77443"
        ),
        Store(
            id = "store_beltola",
            name = "Maa Kamakhya Samagri Kendra",
            locality = "Beltola",
            address = "Beltola Chariali Market Complex, Guwahati",
            rating = 4.75f,
            distanceKm = 3.2,
            deliveryTimeMin = 35,
            isVerified = true,
            phone = "+91 98644 88554"
        ),
        Store(
            id = "store_sixmile",
            name = "Mahadev Puja Bhandar",
            locality = "Six Mile",
            address = "VIP Road, Near Six Mile Flyover, Guwahati",
            rating = 4.7f,
            distanceKm = 4.1,
            deliveryTimeMin = 30,
            isVerified = true,
            phone = "+91 98645 99665"
        )
    )

    val samagriItems: List<SamagriItem> = listOf(
        SamagriItem(
            id = "sam_gamusa",
            name = "Assam Pavitra Cotton Gamusa (Pair)",
            localName = "শুদ্ধ কপাহী গামোচা",
            category = "Vastras",
            price = 260,
            weightUnit = "2 Pieces",
            storeId = "store_ganeshguri",
            storeName = "Assam Vedic Store & Bhandar",
            description = "Auspicious handloom Assamese gamusa with traditional red phool motifs, mandatory for pandit and puja altar."
        ),
        SamagriItem(
            id = "sam_desi_ghee",
            name = "Pure Desi Cow Ghee (Shuddh Ghrit)",
            localName = "শুদ্ধ গৰুৰ ঘিউ",
            category = "Hawan & Diya",
            price = 390,
            weightUnit = "500 ml",
            storeId = "store_kamakhya",
            storeName = "Kamakhya Pavitra Samagri Bhandar",
            description = "Bilona method desi cow ghee, completely pure and ideal for akhand diya and hawan aahuti."
        ),
        SamagriItem(
            id = "sam_havan_pack",
            name = "Complete Hawan Samagri Herbs Box",
            localName = "সম্পূৰ্ণ হোমৰ সামগ্ৰী",
            category = "Hawan & Diya",
            price = 240,
            weightUnit = "500 grams",
            storeId = "store_uzanbazar",
            storeName = "Brahmaputra Pavitra Store",
            description = "Blended with Guggal, Jatamansi, Kapoor Kachri, Lotus seeds, Bhojpatra and sacred medicinal herbs."
        ),
        SamagriItem(
            id = "sam_ganga_jal",
            name = "Sacred Ganga & Brahmaputra Sangam Jal",
            localName = "পৱিত্ৰ গঙ্গা আৰু ব্ৰহ্মপুত্ৰৰ জল",
            category = "Pavitra Jal",
            price = 95,
            weightUnit = "500 ml bottle",
            storeId = "store_uzanbazar",
            storeName = "Brahmaputra Pavitra Store",
            description = "Consecrated holy river water sealed in airtight devotional container."
        ),
        SamagriItem(
            id = "sam_brass_diya",
            name = "Pure Brass Kuber Diya with Stand",
            localName = "পিতলৰ চাকি",
            category = "Brass Utensils",
            price = 320,
            weightUnit = "1 Piece",
            storeId = "store_ganeshguri",
            storeName = "Assam Vedic Store & Bhandar",
            description = "Heavy solid brass diya with intricate engraving, long burning reservoir."
        ),
        SamagriItem(
            id = "sam_camphor",
            name = "Bhimseni Pure Flake Camphor (Kapur)",
            localName = "ভীমসেনী কৰ্পূৰ",
            category = "Hawan & Diya",
            price = 160,
            weightUnit = "100 grams",
            storeId = "store_kamakhya",
            storeName = "Kamakhya Pavitra Samagri Bhandar",
            description = "100% pure edible grade organic camphor, burns clean with no black smoke residue."
        ),
        SamagriItem(
            id = "sam_chandan",
            name = "Natural Sandalwood Stick & Stone Slab",
            localName = "প্ৰাকৃতিক চন্দন কাঠ আৰু চকা",
            category = "Daily Puja",
            price = 299,
            weightUnit = "Set of 2",
            storeId = "store_beltola",
            storeName = "Maa Kamakhya Samagri Kendra",
            description = "Authentic Mysore red/yellow chandan stick with granite rubbing circular slab."
        ),
        SamagriItem(
            id = "sam_kamakhya_sindoor",
            name = "Maa Kamakhya Temple Shuddh Sindoor",
            localName = "মা কামাখ্যাৰ শুদ্ধ সেন্দূৰ",
            category = "Daily Puja",
            price = 85,
            weightUnit = "50 grams",
            storeId = "store_kamakhya",
            storeName = "Kamakhya Pavitra Samagri Bhandar",
            description = "Blessed herbal vermillion free from chemical lead, deep vibrant auspicious red."
        ),
        SamagriItem(
            id = "sam_flower_garland",
            name = "Fresh Marigold & Bilva Patra Basket",
            localName = "কেঁচা ফুল আৰু বেলপাত",
            category = "Daily Puja",
            price = 140,
            weightUnit = "Fresh Morning Pack",
            storeId = "store_sixmile",
            storeName = "Mahadev Puja Bhandar",
            description = "Freshly harvested marigold garland, 21 bilva leaves, 5 red hibiscus flowers and tulsi."
        ),
        SamagriItem(
            id = "sam_dhoop_loban",
            name = "Pure Guggal & Loban Sambrani Cups",
            localName = "শুদ্ধ ধূপ আৰু লোবান",
            category = "Hawan & Diya",
            price = 180,
            weightUnit = "Box of 12 Cups",
            storeId = "store_beltola",
            storeName = "Maa Kamakhya Samagri Kendra",
            description = "Charcoal-free cow dung base filled with organic loban and frankincense."
        )
    )

    val activeOffers: List<OfferBanner> = listOf(
        OfferBanner(
            id = "off_1",
            title = "15% Off Complete Packages",
            subtitle = "Guwahati festive season special discount",
            code = "KAMAKHYA15",
            discountPercent = 15,
            tag = "Most Popular"
        ),
        OfferBanner(
            id = "off_2",
            title = "Free Panchamrit Kit",
            subtitle = "Included free on any verified Pandit booking",
            code = "VEDICFREE",
            discountPercent = 10,
            tag = "Pandit Special"
        ),
        OfferBanner(
            id = "off_3",
            title = "₹50 Off Samagri Delivery",
            subtitle = "On your first partner store order above ₹299",
            code = "GUWAHATI50",
            discountPercent = 5,
            tag = "Quick Delivery"
        )
    )

    // Reactive Room Flow
    val allBookings: Flow<List<BookingEntity>> = bookingDao.getAllBookings()
    val allOrders: Flow<List<OrderEntity>> = orderDao.getAllOrders()
    val cartItems: Flow<List<CartItemEntity>> = cartDao.getAllCartItems()

    suspend fun createBooking(
        bookingType: String,
        pujaName: String,
        pujariName: String,
        date: String,
        timeSlot: String,
        locality: String,
        address: String,
        contactName: String,
        phone: String,
        amount: Int,
        paymentMethod: String
    ): String {
        val id = UUID.randomUUID().toString()
        val bookingCode = "PG-BKG-" + (1000..9999).random()
        val paymentStatus = if (paymentMethod == "Cash on Completion") "Pending (Pay on Completion)" else "Paid (Online Verified)"
        val entity = BookingEntity(
            id = id,
            bookingCode = bookingCode,
            bookingType = bookingType,
            pujaName = pujaName,
            pujariName = pujariName,
            date = date,
            timeSlot = timeSlot,
            locality = locality,
            address = address,
            contactName = contactName,
            phone = phone,
            amount = amount,
            paymentMethod = paymentMethod,
            paymentStatus = paymentStatus,
            status = "CONFIRMED",
            timestamp = System.currentTimeMillis()
        )
        bookingDao.insertBooking(entity)
        return bookingCode
    }

    suspend fun updateBookingRating(id: String, rating: Int, comment: String) {
        bookingDao.updateRating(id, rating, comment)
    }

    suspend fun updateBookingStatus(id: String, status: String) {
        bookingDao.updateStatus(id, status)
    }

    suspend fun createOrder(
        storeName: String,
        itemsSummary: String,
        itemCount: Int,
        totalAmount: Int,
        locality: String,
        deliveryAddress: String,
        phone: String,
        paymentMethod: String
    ): String {
        val id = UUID.randomUUID().toString()
        val orderCode = "PG-ORD-" + (1000..9999).random()
        val paymentStatus = if (paymentMethod == "Cash on Delivery") "Pending (COD)" else "Paid (Online Verified)"
        val entity = OrderEntity(
            id = id,
            orderCode = orderCode,
            storeName = storeName,
            itemsSummary = itemsSummary,
            itemCount = itemCount,
            totalAmount = totalAmount,
            locality = locality,
            deliveryAddress = deliveryAddress,
            phone = phone,
            paymentMethod = paymentMethod,
            paymentStatus = paymentStatus,
            status = "ORDER_PLACED",
            estimatedTime = "30-45 mins",
            timestamp = System.currentTimeMillis()
        )
        orderDao.insertOrder(entity)
        cartDao.clearCart()
        return orderCode
    }

    suspend fun updateOrderStatus(id: String, status: String) {
        orderDao.updateOrderStatus(id, status)
    }

    suspend fun addToCart(item: SamagriItem, qty: Int = 1) {
        val cartEntity = CartItemEntity(
            itemId = item.id,
            name = item.name,
            category = item.category,
            price = item.price,
            weightUnit = item.weightUnit,
            storeName = item.storeName,
            quantity = qty
        )
        cartDao.insertOrUpdate(cartEntity)
    }

    suspend fun removeFromCart(itemId: String) {
        cartDao.deleteItem(itemId)
    }

    suspend fun updateCartQuantity(itemId: String, quantity: Int) {
        if (quantity <= 0) {
            cartDao.deleteItem(itemId)
        } else {
            cartDao.updateQuantity(itemId, quantity)
        }
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    // Seed default booking & order if empty so the user immediately experiences tracking and reviews
    suspend fun seedInitialDataIfEmpty(firstBooking: Boolean = true) {
        // Will be called from ViewModel if needed
    }
}

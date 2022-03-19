package com.example.myapplication.shop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ShopAdapter
import com.example.myapplication.databinding.FragmentShopBinding
import com.example.myapplication.model.ProductModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class ShopFragment : Fragment(R.layout.fragment_shop), IProductClickListener {
    private lateinit var binding: FragmentShopBinding
    private lateinit var itemAdapter: ShopAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShopBinding.inflate(inflater, container, false)
        binding.rvProductList.layoutManager = LinearLayoutManager(context)
        itemAdapter = ShopAdapter( loadData(), this@ShopFragment)
        binding.rvProductList.adapter = itemAdapter
        db = FirebaseFirestore.getInstance()
        return binding.root
    }



    override fun minus(product: ProductModelClass, position: Int) {
        if (product.productAmount > 0) {
            product.productAmount--
            itemAdapter.notifyItemChanged(position)
        }
    }

    override fun cartButton(product: ProductModelClass, position: Int) {
        if (product.productAmount >= 1) {
            val docIdRef: DocumentReference = db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!)
            docIdRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        Toast.makeText(
                            context,
                            "product is already in cart :)",
                            Toast.LENGTH_SHORT
                        ).show()
                        db.collection(FirebaseAuth.getInstance().currentUser!!.uid)
                            .get()
                            .addOnSuccessListener { result ->
                                for (document in result) {
                                    if (document.data.getValue("productName") == product.productName!!) {
                                        var i: Int = ((document.data.getValue("productAmount")) as Number).toInt() + product.productAmount
                                        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!).update("productAmount", i)
                                    }
                                }

                            }
                    } else {
                        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!).set(product)
                        Toast.makeText(
                            context,
                            "${product.productName} have been added to cart.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.d("firestore", "Failed with: ", task.exception)
                }
            }
        } else {
            Toast.makeText(
                context,
                "You must choose atleast 1kg of ${product.productName} to add it.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun show(product: ProductModelClass) {
        val bundle = bundleOf("productName" to product.productName,
        "productOrigin" to product.productOrigin,
        "productClass" to product.productClass,
        "productImage" to product.productImage,
        "productPrice" to product.productPrice,
        "productAmount" to product.productAmount,
        "productInfo" to product.productInfo)
        findNavController().navigate(R.id.action_shopFragment_to_productDetailFragment,bundle)
    }


    override fun add(product: ProductModelClass, position: Int) {
        product.productAmount++
        itemAdapter.notifyItemChanged(position)
    }


    private fun loadData(): ArrayList<ProductModelClass> {
        val productList: ArrayList<ProductModelClass> = ArrayList()
        productList.add(ProductModelClass("Apples", "Kazakhstan", "f", R.drawable.f_apples, 2.0, 0,"An apple is an edible fruit produced by an apple tree (Malus domestica). Apple trees are cultivated worldwide and are the most widely grown species in the genus Malus. The tree originated in Central Asia, where its wild ancestor, Malus sieversii, is still found today."))
        productList.add(ProductModelClass("Apricots", "Armenia", "f", R.drawable.f_apricots, 2.5, 0,"Apricot first appeared in English in the 16th century as abrecock from the Middle French aubercot or later abricot,[2] from Spanish albaricoque and Catalan a(l)bercoc, in turn from Arabic الْبَرْقُوق\u200E (al-barqūq, \"the plums\"), from Byzantine Greek βερικοκκίᾱ (berikokkíā, \"apricot tree\"), derived from late Greek πραικόκιον (praikókion, \"apricot\") from Latin [persica (\"peach\")] praecocia (praecoquus, \"early ripening\")."))
        productList.add(ProductModelClass("Bananas", "Philippines", "f", R.drawable.f_bananas, 3.0, 0,"A banana is an elongated, edible fruit – botanically a berry[1][2] – produced by several kinds of large herbaceous flowering plants in the genus Musa.[3] In some countries, bananas used for cooking may be called \"plantains\", distinguishing them from dessert bananas. The fruit is variable in size, color, and firmness, but is usually elongated and curved, with soft flesh rich in starch covered with a rind, which may be green, yellow, red, purple, or brown when ripe."))
        productList.add(ProductModelClass("Grapefruits", "Jamaica", "f", R.drawable.f_grapefruits, 2.5, 0,"The grapefruit (Citrus × paradisi) is a subtropical citrus tree known for its relatively large, sour to semi-sweet, somewhat bitter fruit.[1] The interior flesh is segmented and varies in color from pale yellow to dark pink. "))
        productList.add(ProductModelClass("Kiwis", "China", "f", R.drawable.f_kiwis, 4.0, 0,"Kiwifruit (often shortened to kiwi in North America and continental Europe) or Chinese gooseberry is the edible berry of several species of woody vines in the genus Actinidia.[1][2] The most common cultivar group of kiwifruit (Actinidia deliciosa 'Hayward')[3] is oval, about the size of a large hen's egg: 5–8 centimetres (2–3 inches) in length and 4.5–5.5 cm (1+3⁄4–2+1⁄4 in) in diameter. It has a thin, fuzzy, fibrous, tart but edible light brown skin and light green or golden flesh with rows of tiny, black, edible seeds. The fruit has a soft texture with a sweet and unique flavour. "))
        productList.add(ProductModelClass("Limes", "Mexico", "f", R.drawable.f_limes, 3.0, 0,"A lime (from French lime, from Arabic līma, from Persian līmū, \"lemon\")[1] is a citrus fruit, which is typically round, green in color, 3–6 centimetres (1.2–2.4 in) in diameter, and contains acidic juice vesicles.[2]\n" + "\n" + "There are several species of citrus trees whose fruits are called limes, including the Key lime (Citrus aurantiifolia), Persian lime, Makrut lime, and desert lime."))
        productList.add(ProductModelClass("Mandarins", "Japan ", "f", R.drawable.f_mandarins, 3.0, 0,"The mandarin orange (Citrus reticulata), also known as the mandarin or mandarine, is a small citrus tree fruit. Treated as a distinct species of orange,[1] it is usually eaten plain or in fruit salads.[1] Tangerines are a group of orange-coloured citrus fruit consisting of hybrids of mandarin orange with some pomelo contribution. "))
        productList.add(ProductModelClass("Mangoes", "Portugal", "f", R.drawable.f_mangoes, 3.0, 0,"A mango is an edible stone fruit produced by the tropical tree Mangifera indica which is believed to have originated from the region between northwestern Myanmar, Bangladesh, and northeastern India.[1] M. indica has been cultivated in South and Southeast Asia since ancient times resulting in two distinct types of modern mango cultivars: the \"Indian type\" and the \"Southeast Asian type\".[2][3] Other species in the genus Mangifera also produce edible fruits that are also called \"mangoes\", the majority of which are found in the Malesian ecoregion."))
        productList.add(ProductModelClass("Nectarines", "China", "f", R.drawable.f_nectarines, 3.0, 0,"The variety P. persica var. nucipersica (or var. nectarina), commonly called nectarine, has a smooth skin. It is on occasion referred to as a \"shaved peach\" or \"fuzzless peach\", due to its lack of fuzz or short hairs. Though fuzzy peaches and nectarines are regarded commercially as different fruits, with nectarines often erroneously believed to be a crossbreed between peaches and plums, or a \"peach with a plum skin\", nectarines belong to the same species as peaches."))

        productList.add(ProductModelClass("Pears", "China", "f", R.drawable.f_pears, 3.0, 0,"Pears are fruits produced and consumed around the world, growing on a tree and harvested in the Northern Hemisphere in late summer into October. The pear tree and shrub are a species of genus Pyrus /ˈpaɪrəs/, in the family Rosaceae, bearing the pomaceous fruit of the same name. Several species of pears are valued for their edible fruit and juices, while others are cultivated as trees. "))
        productList.add(ProductModelClass("Plums", "China", "f", R.drawable.f_plums, 3.0, 0,"A plum is a fruit of some species in Prunus subg. Prunus. Mature plum fruits may have a dusty-white waxy coating that gives them a glaucous appearance. This is an epicuticular wax coating and is known as \"wax bloom\". Dried plums are called prunes, which have a dark, wrinkled appearance."))
        productList.add(ProductModelClass("Raspberries", "Turkey", "f", R.drawable.f_raspberries, 3.0, 0,"The raspberry is the edible fruit of a multitude of plant species in the genus Rubus of the rose family, most of which are in the subgenus Idaeobatus. The name also applies to these plants themselves. Raspberries are perennial with woody stems. World production of raspberries in 2020 was 895,771 tonnes, led by Russia with 20% of the total."))


        productList.add(ProductModelClass("Broccoli", "Italy ", "v", R.drawable.v_broccoli, 3.0, 0,"Broccoli (Brassica oleracea var. italica) is an edible green plant in the cabbage family (family Brassicaceae, genus Brassica) whose large flowering head, stalk and small associated leaves are eaten as a vegetable. Broccoli is classified in the Italica cultivar group of the species Brassica oleracea. Broccoli has large flower heads, usually dark green, arranged in a tree-like structure branching out from a thick stalk which is usually light green. The mass of flower heads is surrounded by leaves. Broccoli resembles cauliflower, which is a different but closely related cultivar group of the same Brassica species. "))

        productList.add(ProductModelClass("Cabbages", "Europe", "v", R.drawable.v_cabbage, 3.0, 0,"Cabbage, comprising several cultivars of Brassica oleracea, is a leafy green, red (purple), or white (pale green) biennial plant grown as an annual vegetable crop for its dense-leaved heads. It is descended from the wild cabbage (B. oleracea var. oleracea), and belongs to the \"cole crops\" or brassicas, meaning it is closely related to broccoli and cauliflower (var. botrytis); Brussels sprouts (var. gemmifera); and Savoy cabbage (var. sabauda). "))
        productList.add(ProductModelClass("Cauliflowers", "Cyprus", "v", R.drawable.v_cauliflower, 3.0, 0,"Cauliflower is one of several vegetables in the species Brassica oleracea in the genus Brassica, which is in the Brassicaceae (or Mustard) family. It is an annual plant that reproduces by seed. Typically, only the head is eaten – the edible white flesh sometimes called \"curd\" (with a similar appearance to cheese curd)."))
        productList.add(ProductModelClass("Celeries", "Switzerland", "v", R.drawable.v_celery, 3.0, 0,"Celery (Apium graveolens) is a marshland plant in the family Apiaceae that has been cultivated as a vegetable since antiquity. Celery has a long fibrous stalk tapering into leaves. Depending on location and cultivar, either its stalks, leaves or hypocotyl are eaten and used in cooking. Celery seed powder is used as a spice. "))
        productList.add(ProductModelClass("Cucumbers", "India", "v", R.drawable.v_cucumber, 3.0, 0,"Cucumber (Cucumis sativus) is a widely-cultivated creeping vine plant in the Cucurbitaceae family that bears usually cylindrical fruits, which are used as vegetables.[1] Considered an annual plant,[2] there are three main varieties of cucumber — slicing, pickling, and burpless/seedless — within which several cultivars have been created."))
        productList.add(ProductModelClass("Garlics", "Iran", "v", R.drawable.v_garlic, 3.0, 0,"Garlic (Allium sativum) is a species of bulbous flowering plant in the genus Allium. Its close relatives include the onion, shallot, leek, chive,[2] Welsh onion and Chinese onion.[3] It is native to Central Asia and northeastern Iran and has long been used as a seasoning worldwide, with a history of several thousand years of human consumption and use."))
        productList.add(ProductModelClass("Lettuces", "Egypt", "v", R.drawable.v_lettuce, 3.0, 0,"Lettuce (Lactuca sativa) is an annual plant of the daisy family, Asteraceae. It is most often grown as a leaf vegetable, but sometimes for its stem and seeds. Lettuce is most often used for salads, although it is also seen in other kinds of food, such as soups, sandwiches and wraps; it can also be grilled."))
        productList.add(ProductModelClass("Onions", "Pakistan", "v", R.drawable.v_onion, 3.0, 0,"The onion (Allium cepa L., from Latin cepa meaning \"onion\"), also known as the bulb onion or common onion, is a vegetable that is the most widely cultivated species of the genus Allium. The shallot is a botanical variety of the onion which was classified as a separate species until 2010.[2][3]: 21  Its close relatives include garlic, scallion, leek, chive,[4] and Chinese onion."))

        productList.add(ProductModelClass("Pumpkins", "Mexico", "v", R.drawable.v_pumpkin, 3.0, 0,"A pumpkin is a cultivar of winter squash that is round with smooth, slightly ribbed skin, and is most often deep yellow to orange in coloration.[1] The thick shell contains the seeds and pulp. The name is most commonly used for cultivars of Cucurbita pepo, but some cultivars of Cucurbita maxima, C. argyrosperma, and C. moschata with similar appearance are also sometimes called \"pumpkins\"."))

        productList.add(ProductModelClass("Silverbeets", "Spain", "v", R.drawable.v_silverbeet, 3.0, 0,"It is an annual plant (rarely biennial), growing as tall as 30 cm (1 ft). Spinach may overwinter in temperate regions. The leaves are alternate, simple, ovate to triangular, and very variable in size: 2–30 cm (1–12 in) long and 1–15 cm (0.4–5.9 in) broad, with larger leaves at the base of the plant and small leaves higher on the flowering stem. The flowers are inconspicuous, yellow-green, 3–4 mm (0.1–0.2 in) in diameter, and mature into a small, hard, dry, lumpy fruit cluster 5–10 mm (0.2–0.4 in) across containing several seeds. "))
        productList.add(ProductModelClass("Spinachs", "Persia", "v", R.drawable.v_spinach, 3.0, 0,"Spinach (Spinacia oleracea) is a leafy green flowering plant native to central and western Asia. It is of the order Caryophyllales, family Amaranthaceae, subfamily Chenopodioideae. Its leaves are a common edible vegetable consumed either fresh, or after storage using preservation techniques by canning, freezing, or dehydration."))
        productList.add(ProductModelClass("Zucchinies", "Mesoamerica", "v", R.drawable.v_zucchini, 3.0, 0,"The zucchini (/zuːˈkiːni/ (audio speaker iconlisten); plural: zucchini or zucchinis),[1] courgette (/kʊərˈʒɛt/; plural: courgettes) or baby marrow (Cucurbita pepo)[2] is a summer squash, a vining herbaceous plant whose fruit are harvested when their immature seeds and epicarp (rind) are still soft and edible. It is closely related, but not identical, to the marrow; its fruit may be called marrow when mature."))
        return productList
    }

}
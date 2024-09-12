package view

import BottomNavigationDirection
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import controller.RedirectionController

/**
 * HomePage
 *
 * This activity serves as the main page of the application, displaying a ViewPager2 with tabs for Hot Coffee, Cold Coffee, and Other Coffee.
 */
class HomePage : AppCompatActivity() {

    /**
     * onCreate
     *
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Find the ImageView for the shopping cart
        val imageCart: ImageView = findViewById(R.id.imagecart)

        // Initialize ViewPager2 and TabLayout
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.coffeetabs)

        // Create an instance of CoffeeFragmentAdapter
        val adapter = CoffeeFragmentAdapter(this)

        // Set the adapter for the ViewPager2
        viewPager.adapter = adapter

        // Attach the ViewPager2 to the TabLayout using TabLayoutMediator
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Hot Coffee"
                1 -> tab.text = "Cold Coffee"
                2 -> tab.text = "Other Coffee"
            }
        }.attach()

        // Set a click listener for the shopping cart icon
        imageCart.setOnClickListener {
            RedirectionController().Redirect(this, ShoppingCart::class.java)
        }

        // Call the method to set up the bottom navigation view
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomnavhome)
        BottomNavigationDirection().callBottomNavigation(bottomNavigationView, this)
    }

    /**
     * onBackPressed
     *
     * Called when the activity has detected the user's press of the back key.
     */
    override fun onBackPressed() {
        RedirectionController().Redirect(this, MainActivity::class.java)
    }
}

package view

import com.example.myapplication.MainActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import controller.RedirectionController

/**
 * AdminPanel
 *
 * This activity serves as the main panel for the admin after successful login. It includes a navigation drawer
 * for easy navigation between different fragments, such as managing coffee items, orders, and viewing ratings.
 */
class AdminPanel : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    lateinit var toggle: ActionBarDrawerToggle

    /**
     * onCreate
     *
     * Called to do initial creation of the activity. This is where you should do all of your normal static
     * set up: create views, bind data to lists, etc.
     *
     * @param savedInstanceState: If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Note: Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Welcome Admin"

        setupDrawerContent(navigationView)
        navigateToFragment(AdminCoffeeManageFragment())
    }

    /**
     * onOptionsItemSelected
     *
     * This method is called when an item in the options menu is selected.
     *
     * @param item: The selected MenuItem.
     * @return true if the item is handled, false otherwise.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * setupDrawerContent
     *
     * Sets up the navigation drawer content and handles item selections.
     *
     * @param navigationView: The NavigationView instance.
     */
    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_managecoffeemenu -> {
                    navigateToFragment(AdminCoffeeManageFragment())
                    true
                }
                R.id.navigation_manageorder -> {
                    navigateToFragment(ManageOrderFragment())
                    true
                }
                R.id.navigation_rating -> {
                    navigateToFragment(RatingViewFragment())
                    true
                }
                else -> false
            }
        }
    }

    /**
     * navigateToFragment
     *
     * Navigates to the specified fragment and closes the navigation drawer.
     *
     * @param fragment: The target Fragment to navigate to.
     */
    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    /**
     * onBackPressed
     *
     * Overrides the default behavior of the back button to show an exit confirmation dialog.
     */
    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Exit Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to go back? You will be logged out.")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            RedirectionController().Redirect(this, MainActivity::class.java)
        }
        alertDialogBuilder.setNegativeButton("No") { _, _ ->
        }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}

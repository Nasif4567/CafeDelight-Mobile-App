import android.content.Context
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.myapplication.R
import controller.RedirectionController
import view.HomePage
import view.NotificationView
import view.ProfileActivity

/**
 * The `BottomNavigationDirection` class provides utility methods for handling bottom navigation operations.
 *
 * @constructor Creates an instance of [BottomNavigationDirection].
 */
class BottomNavigationDirection {

    /**
     * Sets up the behavior of the bottom navigation view when a menu item is selected.
     *
     * @param bottomNavigationView The bottom navigation view to be configured.
     * @param context The context of the application.
     */
    fun callBottomNavigation(bottomNavigationView: BottomNavigationView, context: Context) {
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Redirect to the home page
                    RedirectionController().Redirect(context, HomePage::class.java)
                    true
                }
                R.id.navigation_notification -> {
                    // Redirect to the notification view
                    RedirectionController().Redirect(context, NotificationView::class.java)
                    true
                }
                R.id.navigation_profile -> {
                    // Redirect to the profile activity
                    RedirectionController().Redirect(context, ProfileActivity::class.java)
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Selects the specified item programmatically in the bottom navigation view.
     *
     * @param bottomNavigationView The bottom navigation view.
     * @param itemId The ID of the item to be selected.
     */
    fun selectBottomNavigationItem(bottomNavigationView: BottomNavigationView, itemId: Int) {
        // Programmatically select the specified item
        bottomNavigationView.selectedItemId = itemId
    }
}


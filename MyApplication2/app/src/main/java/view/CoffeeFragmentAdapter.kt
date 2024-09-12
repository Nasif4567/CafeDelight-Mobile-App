package view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * CoffeeFragmentAdapter
 *
 * This class is an adapter for the ViewPager2 in the HomePage activity. It determines the number of fragments
 * and provides the appropriate fragment for each position.
 *
 * @param fragment The parent fragment to which this adapter belongs.
 */
class CoffeeFragmentAdapter(fragment: HomePage) : FragmentStateAdapter(fragment) {

    /**
     * getItemCount
     *
     * Returns the total number of fragments managed by this adapter.
     *
     * @return The total number of fragments.
     */
    override fun getItemCount(): Int {
        return 3
    }

    /**
     * createFragment
     *
     * Creates and returns the appropriate fragment for the given position.
     *
     * @param position The position of the fragment.
     * @return The fragment for the given position.
     * @throws IllegalArgumentException if the position is invalid.
     */
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HotCoffeeFragment()
            1 -> ColdCoffeeFragment()
            2 -> OtherCoffeeFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}

package com.guerra.enrico.sera.ui.todos.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.models.Category
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Created by enrico
 * on 16/08/2019.
 */
class SearchTasksAutocompleteAdapter constructor(
        context: Context,
        private var list: List<Category>
) : ArrayAdapter<Category>(context, R.layout.item_category) {

  private val filter =
    CategoryAutocompleteFilter(
      list,
      this
    )

  fun updateList(newList: List<Category>) {
    list = newList
    notifyDataSetChanged()
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view = convertView
            ?: (context as Activity).layoutInflater.inflate(R.layout.item_category, parent, false)
    view.labelCategoryName.text = getItem(position)?.name
    return view
  }

  override fun getItem(position: Int): Category? = list[position]

  override fun getCount(): Int = list.size

  override fun getFilter(): Filter = filter

  internal class CategoryAutocompleteFilter(
          private val list: List<Category>,
          private val adapter: SearchTasksAutocompleteAdapter
  ) : Filter() {
    private var suggestions = mutableListOf<Category>()

    override fun convertResultToString(resultValue: Any?): CharSequence = if (resultValue == null) "" else (resultValue as Category).name

    override fun performFiltering(chars: CharSequence?): FilterResults {
      val results = FilterResults()
      if (chars.isNullOrEmpty()) {
        return results
      }
      suggestions.clear()
      val regex = "(?=.*$chars)".toRegex(setOf(RegexOption.IGNORE_CASE))
      suggestions.addAll(list.filter { category ->
        category.name.contains(regex)
      })
      results.values = suggestions
      results.count = suggestions.size
      return results
    }

    override fun publishResults(chars: CharSequence?, result: FilterResults?) {
      if (result?.values == null) {
        adapter.updateList(emptyList())
        return
      }
      @Suppress("UNCHECKED_CAST")
      adapter.updateList(result.values as List<Category>)
    }
  }
}

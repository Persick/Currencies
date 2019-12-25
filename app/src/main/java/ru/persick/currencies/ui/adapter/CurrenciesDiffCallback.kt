package ru.persick.currencies.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.persick.currencies.ui.vo.CurrencyVO

class CurrenciesDiffCallback(
    private val oldData: List<CurrencyVO>,
    private val newData: List<CurrencyVO>
) : DiffUtil.Callback() {

    companion object {
        const val UPDATE_ALL = 0
        const val UPDATE_RATE = 1
    }

    override fun getNewListSize(): Int =
        newData.size

    override fun getOldListSize(): Int =
        oldData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldData[oldItemPosition].code == newData[newItemPosition].code

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldData[oldItemPosition] == newData[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldData[oldItemPosition]
        val newItem = newData[newItemPosition]

        return if (oldItem.image != newItem.image || oldItem.code != newItem.code || oldItem.name != newItem.name) {
            UPDATE_ALL
        } else if (oldItem.rate != newItem.rate) {
            UPDATE_RATE
        } else {
            null
        }
    }
}
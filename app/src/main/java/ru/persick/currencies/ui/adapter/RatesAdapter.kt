package ru.persick.currencies.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import ru.persick.currencies.R
import ru.persick.currencies.Util.decimalFormat
import ru.persick.currencies.Util.decimalFormatToDouble
import ru.persick.currencies.api.model.Currency
import ru.persick.currencies.ui.vo.CurrencyVO
import kotlin.collections.ArrayList


class RatesAdapter(
    private val onQuantityChanged: (newCurrency: Currency, needToScroll: Boolean) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mData = ArrayList<CurrencyVO>()

    fun refresh(newData: List<CurrencyVO>) {
        val diffCallback = CurrenciesDiffCallback(mData, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback, true)
        diffResult.dispatchUpdatesTo(this)

        mData.clear()
        mData.addAll(newData)
    }

    override fun getItemCount(): Int = mData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(mData[position])
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            when (holder) {
                is ItemViewHolder -> {
                    payloads.forEach { payload ->
                        when (payload as Int) {
                            CurrenciesDiffCallback.UPDATE_ALL -> holder.bind(mData[position])
                            CurrenciesDiffCallback.UPDATE_RATE -> holder.bindRate(mData[position])
                        }
                    }
                }
            }
        }
    }

    private inner class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_rate,
            parent,
            false
        )
    ) {
        private val flagIv = itemView.findViewById<ImageView>(R.id.flagIv)
        private val currencyTv = itemView.findViewById<TextView>(R.id.currencyTv)
        private val currencyNameTv = itemView.findViewById<TextView>(R.id.currencyNameTv)
        private val quantityEt = itemView.findViewById<EditText>(R.id.quantityEt)
        private val root = itemView.findViewById<ConstraintLayout>(R.id.root)

        private var isTextWatcherEnabled = false
        private lateinit var textWatcher: TextWatcher

        fun bind(item: CurrencyVO) {
            Picasso.with(itemView.context).load(item.image).transform(CropCircleTransformation())
                .into(flagIv)

            currencyTv.text = item.code
            currencyNameTv.text = item.name

            textWatcher = object : TextWatcher {
                override fun afterTextChanged(text: Editable?) {
                    disableTextWatcher()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (quantityEt.isFocused) {
                        val newRate = text.toString().decimalFormatToDouble()
                        onQuantityChanged(Currency(code = item.code, rate = newRate), false)
                    }
                }
            }

            bindRate(item)

            quantityEt.setOnFocusChangeListener { view, onFocus ->
                if (!onFocus) {
                    disableTextWatcher()
                }
            }
        }

        fun bindRate(item: CurrencyVO) {
            disableTextWatcher()
            quantityEt.setText(item.rate.decimalFormat())

            if (item.isBase && layoutPosition == 0) {
                enableTextWatcher()
                quantityEt.requestFocus()
                quantityEt.setSelection(quantityEt.text.length)
            } else {
                quantityEt.clearFocus()
            }

            root.setOnClickListener {
                if (!item.isBase) {
                    val newRate = quantityEt.text.toString().decimalFormatToDouble()
                    onQuantityChanged(Currency(code = item.code, rate = newRate), true)
                }
            }
        }

        private fun enableTextWatcher() {
            if (!isTextWatcherEnabled)
                quantityEt.addTextChangedListener(textWatcher)

            isTextWatcherEnabled = true
        }

        private fun disableTextWatcher() {
            if (isTextWatcherEnabled)
                quantityEt.removeTextChangedListener(textWatcher)

            isTextWatcherEnabled = false
        }
    }
}
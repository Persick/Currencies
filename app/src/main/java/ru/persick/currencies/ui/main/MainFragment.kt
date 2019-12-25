package ru.persick.currencies.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*
import ru.persick.currencies.App
import ru.persick.currencies.R
import ru.persick.currencies.ui.adapter.RatesAdapter
import ru.persick.currencies.ui.vo.CurrencyVO
import javax.inject.Inject
import android.view.inputmethod.InputMethodManager


class MainFragment : Fragment(), MainView {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var presenter: MainPresenter
    private lateinit var adapter: RatesAdapter
    private var needToScroll = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RatesAdapter { currency, needToScroll ->
            this.needToScroll = needToScroll
            presenter.updateCurrentBaseCurrency(currency)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        recyclerView.setOnTouchListener { v, _ ->
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(v.windowToken, 0)

            false
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this)
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun refreshRates(currencies: List<CurrencyVO>) {
        adapter.refresh(currencies)
        if (needToScroll) {
            (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
            needToScroll = false
        }
    }

    override fun getMainContext(): Context {
        return requireContext()
    }
}

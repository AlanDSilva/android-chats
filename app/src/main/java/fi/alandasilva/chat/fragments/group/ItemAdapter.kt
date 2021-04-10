package fi.alandasilva.chat.fragments.group

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fi.alandasilva.chat.databinding.ItemGroupBinding
import fi.alandasilva.chat.model.Group

class ItemAdapter(private val dataset: ArrayList<Group>)
    : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    private val TAG = "GroupFragmentItemAdapter"

    class ItemViewHolder(val binding: ItemGroupBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.titleTextView.text = dataset[position].name
        holder.binding.membersTextView.text = dataset[position].photoUrl
        holder.binding.cardView.setOnClickListener {
            Log.d(TAG, "CardView clicked!")
        }
    }
}
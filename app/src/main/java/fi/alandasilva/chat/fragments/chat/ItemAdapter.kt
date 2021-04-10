package fi.alandasilva.chat.fragments.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fi.alandasilva.chat.databinding.ItemChatBinding
import fi.alandasilva.chat.model.Message

class ItemAdapter(private val dataset: ArrayList<Message>)
    : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    private val TAG = "ChatFragmentItemAdapter"

    class ItemViewHolder(val binding: ItemChatBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
                ItemChatBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.contentTextView.text = dataset[position].content
        holder.binding.authorTextView.text = dataset[position].sender
    }
}
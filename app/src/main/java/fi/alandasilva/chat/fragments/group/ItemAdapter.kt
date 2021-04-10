package fi.alandasilva.chat.fragments.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import fi.alandasilva.chat.databinding.ItemGroupBinding
import fi.alandasilva.chat.model.Group

class ItemAdapter(private val dataset: ArrayList<Group>, private val navController: NavController)
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
            val action = GroupFragmentDirections.actionGroupFragmentToChatFragment(dataset[position].id, dataset[position].name)
            navController.navigate(action)
        }
    }
}
package com.example.doc_schedule.Manager

import com.example.doc_schedule.Domain.DoctorsModel

object WishlistManager {
    private val wishlist = mutableListOf<DoctorsModel>()

    fun add(doctor: DoctorsModel): Boolean {
        if (!contains(doctor)) {
            wishlist.add(doctor)
            return true
        }
        return false
    }

    fun remove(doctor: DoctorsModel): Boolean {
        return wishlist.removeIf { it.Id == doctor.Id }
    }

    fun contains(doctor: DoctorsModel): Boolean {
        return wishlist.any { it.Id == doctor.Id }
    }

    fun fetchWishlist(): List<DoctorsModel> = wishlist.toList()
}

package com.carspotter.CarSpotter.service;

import com.carspotter.CarSpotter.model.Invitation;
import com.carspotter.CarSpotter.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InvitationService {

    private final InvitationRepository invitationRepository;


    public List<Invitation> getInvitations() {
        return invitationRepository.findAll();
    }

    public Invitation getInvitationById(Integer id) {
        return invitationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invitation not found with id: " + id));
    }

    public Invitation saveInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    public void deleteInvitation(Integer id) {
        invitationRepository.deleteById(id);
    }
}

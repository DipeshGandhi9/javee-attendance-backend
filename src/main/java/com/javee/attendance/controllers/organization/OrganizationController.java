package com.javee.attendance.controllers.organization;

import com.javee.attendance.entities.Organization;
import com.javee.attendance.model.JWTUserDetails;
import com.javee.attendance.repositories.OrganizationRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@Api( value = "Organization", description = "API for Organization", tags = { "Organization" } )
@RequestMapping( "/api" )
public class OrganizationController
{
	@Autowired
	private OrganizationRepository organizationRepository;

	@CrossOrigin
	@RequestMapping( value = "/organization", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public Organization createOrganization( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Organization organization )
	{
		organization = organizationRepository.save( organization );
		return organization;
	}

	@CrossOrigin
	@RequestMapping( value = "/organization/{id}", method = RequestMethod.GET,
			produces = "application/json" )
	public Organization getOrganizationById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		Optional<Organization> organizationOptional = organizationRepository.findById( id );
		return organizationOptional.get();
	}

	@CrossOrigin
	@RequestMapping( value = "/organizations", method = RequestMethod.GET,
			produces = "application/json" )
	public List<Organization> getOrganizations( @AuthenticationPrincipal JWTUserDetails jwtUserDetails )
	{
		return organizationRepository.findAll();
	}

	@CrossOrigin
	@RequestMapping( value = "/organization/{id}", method = RequestMethod.PUT,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity<Object> updateOrganization( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Organization organization, @PathVariable Long id )
	{
		Optional<Organization> organizationOptional = organizationRepository.findById( id );
		if (!organizationOptional.isPresent())
			return ResponseEntity.notFound().build();
		organization.setId( id );
		organizationRepository.save( organization );
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@RequestMapping( value = "/organization/{id}", method = RequestMethod.DELETE,
			produces = "application/json" )
	public boolean deleteOrganizationById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		organizationRepository.deleteById( id );
		return true;
	}

}


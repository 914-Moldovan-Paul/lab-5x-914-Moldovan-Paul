// HOSPITALS

describe('HOSPITLAS: Test name exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/hospitals/1',
      }).then(function(response) {
          expect(response.body).have.property('name');
  });
});
});

describe('HOSPITLAS: Test address exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/hospitals/1',
      }).then(function(response) {
          expect(response.body).have.property('address');
  });
});
});

describe('HOSPITLAS: Test register date exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/hospitals/1',
      }).then(function(response) {
          expect(response.body).have.property('registerDate');
  });
});
});

describe('HOSPITLAS: Test not found', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/hospitals/-1',
          failOnStatusCode: false,
      }).then(function(response) {
          expect(response.status).to.eq(404);
  });
});
});

// DOCTORS

describe('DOCTORS: Test id exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/doctors/1',
      }).then(function(response) {
          expect(response.body).have.property('id');
  });
});
});

describe('DOCTORS: Test name exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/doctors/1',
      }).then(function(response) {
          expect(response.body).have.property('name');
  });
});
});

describe('DOCTORS: Test speciality exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/doctors/1',
      }).then(function(response) {
          expect(response.body).have.property('speciality');
  });
});
});

describe('DOCTORS: Test publish date exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/doctors/1',
      }).then(function(response) {
          expect(response.body).have.property('publishDate');
  });
});
});

describe('DOCTORS: Test experience exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/doctors/1',
      }).then(function(response) {
          expect(response.body).have.property('experience');
  });
});
});

describe('DOCTORS: Test shifts exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/doctors/1',
      }).then(function(response) {
          expect(response.body).have.property('shifts');
  });
});
});

describe('DOCTORS: Test id exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/doctors/1',
      }).then(function(response) {
          expect(response.body).have.property('hospitalId');
  });
});
});

describe('DOCTORS: Test department exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/doctors/1',
      }).then(function(response) {
          expect(response.body).have.property('department');
  });
});
});

describe('DOCTORS: Test not found', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/doctors/-1',
          failOnStatusCode: false,
      }).then(function(response) {
          expect(response.status).to.eq(404);
  });
});
});

// USERS

describe('USERS: Test name exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/users/boss',
      }).then(function(response) {
          expect(response.body).have.property('name');
  });
});
});

describe('USERS: Test handle exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/users/boss',
      }).then(function(response) {
          expect(response.body).have.property('handle');
  });
});
});

describe('USERS: Test email exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/users/boss',
      }).then(function(response) {
          expect(response.body).have.property('email');
  });
});
});

describe('USERS: Test birthday exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/users/boss',
      }).then(function(response) {
          expect(response.body).have.property('birthday');
  });
});
});

describe('USERS: Test register date exists', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/users/boss',
      }).then(function(response) {
          expect(response.body).have.property('registeredAt');
  });
});
});

describe('USERS: Test not found', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'https://hospitalorganizer.mooo.com/users/-1',
          failOnStatusCode: false,
      }).then(function(response) {
          expect(response.status).to.eq(404);
  });
});
});
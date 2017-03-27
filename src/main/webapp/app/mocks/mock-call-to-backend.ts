export function mockCallToBackend(mock:any) {
    return {
        body:{
            data:mock
        },
        status:200
    }
}

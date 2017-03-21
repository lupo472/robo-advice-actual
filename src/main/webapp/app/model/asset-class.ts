export class AssetClass {
    private id: number;
    private name: string;

    constructor(id: number, name: string) {
        this.id = id;
        this.name = name;
    }

    setId(id: number): void {
        this.id = id;
    }

    setName(name: string): void {
        this.name = name;
    }

    getId(): number {
        return this.id;
    }

    getName(): string {
        return this.name;
    }
    /*assignColour(): string {
        let id = this.getId();
        switch (id) {
            case 1: return "#4dbd74";
            case 2: return "#63c2de";
            case 3: return "#f8cb00";
            case 4: return "#f86c6b";
        }
    }*/
    assignColour(): string {
        let id = this.getId();
        switch (id) {
            case 1: return "#81C784";
            case 2: return "#4DD0E1";
            case 3: return "#FFD54F";
            case 4: return "#E57373";
        }
    }
}
